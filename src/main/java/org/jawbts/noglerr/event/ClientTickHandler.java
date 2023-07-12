package org.jawbts.noglerr.event;

import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.client.MinecraftClient;

public class ClientTickHandler implements IClientTickHandler {
    public static MinecraftClient mc;

    @Override
    public void onClientTick(MinecraftClient mc) {
        ClientTickHandler.mc = mc;
        if (mc.world != null && mc.player != null) {
            OnTick.onTick(mc);
        }
    }
}