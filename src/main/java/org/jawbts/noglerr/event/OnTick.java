package org.jawbts.noglerr.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.tweak.voice.Vosk;
import org.jawbts.noglerr.util.PlayerMessageSender;

public class OnTick {
    public static void onTick(MinecraftClient mc) {
        ClientPlayerEntity player = mc.player;
        ClientWorld world = mc.world;
        if (player == null || world == null) {
            return;
        }

        // 处理堆积的全局消息发送给给玩家
        PlayerMessageSender.getInstance().send(player);
        // tickUpdateChecker tick
        Utils.TickUpdateChecker.tick();

        // 处理视觉效果
        if (Configs.Toggles.SHOW_VFX.getBooleanValue()) {
            if (Configs.Toggles.SHOW_ENTITY_DETAIL.getBooleanValue()) {
                VarEntityHandler.handleEntityDetail(mc, player, world);
            } else {
                VarEntityHandler.clearAll();
            }
        } else {
            VarEntityHandler.clearAll();
        }

        // Voice To Text tick
        Vosk.setNeedSend(Configs.Toggles.VOICE_TO_TEXT.getBooleanValue());
    }
}
