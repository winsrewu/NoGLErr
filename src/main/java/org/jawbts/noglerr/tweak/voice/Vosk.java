package org.jawbts.noglerr.tweak.voice;

import net.minecraft.client.MinecraftClient;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.io.File;

public class Vosk {
    private static final Vosk INSTANCE = new Vosk();
    private static VoskThread voskThread;
    private static MinecraftClient mc;
    public static Vosk getInstance() {
        return INSTANCE;
    }
    public static void setNeedSend(boolean b) {
        if (voskThread == null) {
            return;
        }
        voskThread.setNeedSend(b);
    }

    public void init() {
        File folder = new File("./", "voskModels");
        folder.mkdir();
        voskThread = new VoskThread();
        voskThread.start();
    }

    public void reload() {
        voskThread.needReload();
    }

    public void sendToChat(String s) {
        mc = ClientTickHandler.mc;
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