package org.jawbts.noglerr.tweak.voice;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.util.PlayerMessageSender;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Vosk {
    private static final Vosk INSTANCE = new Vosk();
    private VoskThread voskThread = null;

    public static Vosk getInstance() {
        return INSTANCE;
    }

    public void tick() {
        if (!Utils.gameReadyCheck()) {
            return;
        }
        boolean needStart = Configs.Toggles.VOICE_TO_TEXT.getBooleanValue();
        boolean needSend = Configs.Toggles.MICROPHONE_SWITCH.getBooleanValue();

        if (!needStart) {
            if (voskThread != null) {
                voskThread.setShouldStop();
                voskThread = null;
            }
            return;
        }
        if (voskThread == null) {
            voskThread = new VoskThread();
            voskThread.start();
        }
        voskThread.setMicrophone(needSend);
    }

    public void reload() {
        if (voskThread == null) return;
        voskThread.setShouldStop();
        while(voskThread.isRunning());
        voskThread = new VoskThread();
    }

    public void sendToChat(String s) {
        if (!Utils.gameReadyCheck()) return;

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
            s = "[Format Error]";
        }

        if (!Configs.Toggles.VOICE_TO_TEXT_NO_HISTORY.getBooleanValue()) {
            mc.inGameHud.getChatHud().addToMessageHistory(s);
        }
        if (ClientTickHandler.mc.player == null) return;
        if (s.startsWith("/")) {
            ClientTickHandler.player.networkHandler.sendChatCommand(s.substring(1));
        } else {
            ClientTickHandler.player.networkHandler.sendChatMessage(s);
        }
    }

    public String decrypt(String password, String s) {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory;
        Key key;

        byte[] salt = Base64.getUrlDecoder().decode(s.substring(0, 12));

        try {
            factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
        } catch (NoSuchAlgorithmException e) {
            // 这不应该发生
            throw new RuntimeException(e);
        }

        try {
            key = factory.generateSecret(pbeKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("PBEWITHMD5andDES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // 这不应该发生
            throw new RuntimeException(e);
        }

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt,100);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            // 这不应该发生
            throw new RuntimeException(e);
        }

        byte[] result = Base64.getUrlDecoder().decode(s.substring(12));
        try {
            result = cipher.doFinal(result);
        } catch (IllegalBlockSizeException e) {
            // 这不应该发生
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            PlayerMessageSender.getInstance().add("red", "noglerr.info.wrongVoskPassword");
            return "";
        }

        return new String(result);
    }
}