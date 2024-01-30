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
}
