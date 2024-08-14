package org.jawbts.noglerr.tweak.voice;

import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class VoskThread extends Thread {
    volatile private boolean voskThreadRunning = false;
    volatile private boolean voskModelRunning = false;
    volatile private boolean shouldStop = false;
    volatile private boolean running = false;
    volatile private boolean microphone = false;
    volatile private boolean microphoneShould = false;
    PrintWriter out;


    public void setMicrophone(boolean on) {
        microphoneShould = on;
    }

    public void setShouldStop() {
        shouldStop = true;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        try {
            innerRun();
        } catch (Exception e) {
            NoglerrClient.LOGGER.error("Vosk client socket error.", e);
            PlayerMessageSender.getInstance().add("red", "[Vosk] " + e.getMessage());
        }
    }

    public void innerRun() {
        String serverIp = Configs.Secrets.VOSK_IP.getStringValue();
        String[] strings = serverIp.split(":");
        int portNumber;
        if (strings.length != 2) throw new RuntimeException("Invalid vosk server ip");
        portNumber = Integer.parseInt(strings[1]);

        try (Socket socket = new Socket(strings[0], portNumber);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);
            running = true;

            if (Configs.Toggles.DEBUG.getBooleanValue()) {
                PlayerMessageSender.getInstance().add("Connect to vosk server.");
            }

            // 发送消息到服务器
            out.println("CB" + Configs.Detailed.VOICE_TO_TEXT_PATH.getStringValue() + "+" +
                    Configs.Detailed.MICROPHONE_SAMPLE_RATE.getIntegerValue() + "+" +
                    Configs.Detailed.MICROPHONE_SAMPLE_SIZE_IN_BITS.getIntegerValue() + "+" +
                    Configs.Detailed.MICROPHONE_CHANNELS.getIntegerValue() + "+" +
                    Configs.Detailed.MICROPHONE_FRAME_SIZE.getIntegerValue() + "+" +
                    Configs.Detailed.MICROPHONE_FRAME_RATE.getIntegerValue());

            // 接收服务器的响应
            String response;
            while (!shouldStop) {
                if (in.ready() && (response = in.readLine()) != null) {
                    if (response.startsWith("!_C")) {
                        Vosk.getInstance().sendToChat(
                                Vosk.getInstance().decrypt(Configs.Secrets.VOSK_PASSWORD.getStringValue(),
                                        response.substring(3))
                        );
                    } else if (response.startsWith("!_voskThreadRunning ")) {
                        voskThreadRunning = Boolean.parseBoolean(response.substring(20));
                    } else if (response.startsWith("!_voskModelRunning ")) {
                        voskModelRunning = Boolean.parseBoolean(response.substring(19));
                    } else {
                        PlayerMessageSender.getInstance().add("red", "[Vosk] " + response);
                    }
                }

                if (microphoneShould != microphone) {
                    microphone = microphoneShould;
                    if (microphone) {
                        out.println("CMO");
                    } else {
                        out.println("CMF");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.println("Cshut");
                out.close();
            }
            running = false;
        }
    }
}
