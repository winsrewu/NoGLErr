package org.jawbts.noglerr.tweak.voice;

import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.util.PlayerMessageSender;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoskThread extends Thread {
    private final Pattern pattern = Pattern.compile("\"([^\"]+)\"");
    private Thread thread;
    private TargetDataLine microphone;
    private boolean inited = false;
    volatile private boolean needReload = false;
    volatile private boolean needSend = false;
    private boolean processed = false;
    volatile private boolean needWait = false;

    public void needReload() {
        needReload = true;
        needWait = false;
    }

    public void setNeedSend(boolean b) {
        if (needSend != b) {
            processed = false;
        }
        needSend = b;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void init() throws LineUnavailableException {
        if (inited) {
            return;
        }

        inited = true;

        LibVosk.setLogLevel(LogLevel.INFO);
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();
    }

    public void close() {
        if (microphone != null) {
            microphone.close();
        }
    }

    private String match(String s) {
        Matcher matcher = pattern.matcher(s);
        boolean flag = true;
        while (matcher.find()) {
            if (!flag) {
                return matcher.group(1);
            }
            flag = false;
        }
        return "";
    }

    private void priRun() throws IOException {
        try {
            init();
        } catch (Exception e) {
            close();
            e.printStackTrace();
        }
        try (Model model = new Model("voskModels/" + Configs.Detailed.VOICE_TO_TEXT_PATH.getStringValue());
             Recognizer recognizer = new Recognizer(model, 120000)) {
            try {
                int numBytesRead = 0;
                int CHUNK_SIZE = 1024;

                byte[] b = new byte[4096];

                while (!needReload) {
                    if (needSend) {
                        numBytesRead = microphone.read(b, 0, CHUNK_SIZE);
                    } else if (processed) {
                        continue;
                    }

                    if (recognizer.acceptWaveForm(b, numBytesRead)) {
                        String result = match(recognizer.getResult());

                        // 等处理完了再关
                        processed = true;
                        if (result.equals("")) {
                            continue;
                        }

                        // 结果尝试输出
                        Vosk.getInstance().sendToChat(result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (true) {
            if (needReload) {
                needReload = false;
                PlayerMessageSender.getInstance().add("noglerr.command.succeed");
            }
            if (!needWait) {
                try {
                    priRun();
                } catch (IOException e) {
                    PlayerMessageSender.getInstance().add("noglerr.command.modelNotReady");
                    needWait = true;
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {

                }
            }
        }
    }
}
