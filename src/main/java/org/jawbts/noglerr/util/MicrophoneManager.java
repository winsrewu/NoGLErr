package org.jawbts.noglerr.util;

import javax.sound.sampled.*;

public class MicrophoneManager {
    private static final MicrophoneManager INSTANCE = new MicrophoneManager();
    private TargetDataLine microphone;
    private boolean initFlag = false;

    public static MicrophoneManager getInstance() {
        return INSTANCE;
    }

    public void init() throws LineUnavailableException {
        if (initFlag) {
            return;
        }
        initFlag = true;

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();
    }

    public void close() {
        if (microphone == null) {
            return;
        }
        microphone.close();
        initFlag = false;
    }

    public int read(byte[] b, int off, int len) {
        return microphone.read(b, off, len);
    }
}
