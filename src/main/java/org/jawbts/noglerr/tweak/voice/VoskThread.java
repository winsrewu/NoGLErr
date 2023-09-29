package org.jawbts.noglerr.tweak.voice;

import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.util.MicrophoneManager;
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
    private boolean inited = false;
    volatile private boolean needReload = false;
    volatile private boolean needSend = false;
    volatile private boolean needClose = false;
    private boolean start = true;

    public void needReload() {
        needReload = true;
    }

    public void setNeedSend(boolean b) {
        needSend = b;
    }

    public boolean getNeedSend() {
        return needSend;
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
        start = true;

        LibVosk.setLogLevel(LogLevel.INFO);
        MicrophoneManager.getInstance().init();
    }

    public void close() {
        MicrophoneManager.getInstance().close();
        needClose = true;
    }

    // json中提取字符串
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

    private void proRun() throws IOException {
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

                while (!needReload && !needClose) {
                    if (needSend) {
                        numBytesRead = MicrophoneManager.getInstance().read(b, 0, CHUNK_SIZE);
                    }

                    if (recognizer.acceptWaveForm(b, numBytesRead)) {
                        String result = match(recognizer.getResult());

                        if (result.equals("")) {
                            continue;
                        }

                        // 结果输出
                        Vosk.getInstance().sendToChat(result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (!needClose) {
            if (!Utils.gameReadyCheck()) {
                continue;
            }
            if (start) {
                if (!Utils.playerReadyCheck()) {
                    continue;
                }
                start = false;
            }

            if (needReload) {
                needReload = false;
                PlayerMessageSender.getInstance().add("noglerr.command.succeed");
            }

            try {
                proRun();
            } catch (IOException e) {
                PlayerMessageSender.getInstance().add("noglerr.command.modelNotReady");
                Configs.Toggles.VOICE_TO_TEXT.setBooleanValue(false);
                needClose = true;
            }
        }
    }
}
