package org.jawbts.noglerr.tweak.voice;

import net.minecraft.client.MinecraftClient;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.tweak.Utils;

import java.io.File;
import java.sql.Timestamp;

public class Vosk {
    private static final Vosk INSTANCE = new Vosk();
    private static VoskThread voskThread;
    private static boolean needStart = false;
    private static boolean needSend = false;
    private static boolean preNeedSend = false;
    private static Timestamp sendCloseTime = new Timestamp(System.currentTimeMillis());

    public static Vosk getInstance() {
        return INSTANCE;
    }

    public static void setStatus(boolean needStart, boolean needSend) {
        Vosk.needStart = needStart;
        Vosk.needSend = needSend;
    }

    public void init() {
        File folder = new File("./", "voskModels");
        folder.mkdir();
        voskThread = new VoskThread();
        voskThread.start();
    }

    public static void tick() {
        setStatus(Configs.Toggles.VOICE_TO_TEXT.getBooleanValue(), Configs.Toggles.MICROPHONE_SWITCH.getBooleanValue());

        if (!needStart) {
            if (voskThread != null) {
                voskThread.close();
                voskThread = null;
            }
            return;
        }
        if (voskThread == null) {
            voskThread = new VoskThread();
            voskThread.start();
        }
        if (preNeedSend != needSend) {
            preNeedSend = needSend;
            if (!needSend) {
                sendCloseTime.setTime(System.currentTimeMillis());
            } else {
                voskThread.setNeedSend(true);
            }
        }
        if (!needSend && voskThread.getNeedSend() &&
                sendCloseTime.before(new Timestamp(System.currentTimeMillis() - 500L))) {
            voskThread.setNeedSend(false);
        }
    }

    public void reload() {
        voskThread.needReload();
    }

    public void sendToChat(String s) {
        Utils.gameReadyCheck();

        MinecraftClient mc = ClientTickHandler.mc;
        if (mc == null || mc.player == null) {
            return;
        }

        if (Configs.Toggles.VOICE_TO_TEXT_REMOVE_SPACE.getBooleanValue()) {
            s = s.replaceAll(" ", "");
        }

        try {
            s = String.format(Configs.Detailed.VOICE_TO_TEXT_FORMAT.getStringValue(), s);
        } catch (Exception e) {
            s = "Format Err";
        }

        if (!Configs.Toggles.VOICE_TO_TEXT_NO_HISTORY.getBooleanValue()) {
            mc.inGameHud.getChatHud().addToMessageHistory(s);
        }
        mc.player.sendChatMessage(s);
    }
}