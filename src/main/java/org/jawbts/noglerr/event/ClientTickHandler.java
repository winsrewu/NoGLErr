package org.jawbts.noglerr.event;

import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientTickHandler implements IClientTickHandler {
    public static MinecraftClient mc;
    public static ClientPlayerEntity player;

    @Override
    public void onClientTick(MinecraftClient mc) {
        if (mc.world != null && mc.player != null) {
            OnTick.onTick(mc);
        }
        ClientTickHandler.mc = mc;
        player = mc.player;
    }
}