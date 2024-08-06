package org.jawbts.noglerr.tweak.var.javascript;

import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.tweak.var.javascript.proxy.World;
import org.jawbts.noglerr.util.PlayerMessageSender;

public class Utils {
    public PlayerMessageSender getPms() {
        return PlayerMessageSender.getInstance();
    }

    public void sendMes(String s) {
        PlayerMessageSender.getInstance().add(s);
    }

    public World getWorld() {
        return new World(ClientTickHandler.mc.world);
    }

    public boolean sendMesToPublic(String s) {
        if (ClientTickHandler.mc.player == null) return false;
        ClientTickHandler.mc.player.sendChatMessage(s);
        return true;
    }
}
