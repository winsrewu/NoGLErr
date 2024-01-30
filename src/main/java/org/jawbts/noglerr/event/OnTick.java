package org.jawbts.noglerr.event;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.*;
import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.tweak.voice.Vosk;
import org.jawbts.noglerr.util.PlayerMessageSender;
import org.jawbts.noglerr.util.UpdateChecker;
import org.jawbts.noglerr.util.VersionInfo;

public class OnTick {
    public static void onTick(MinecraftClient mc) {
        ClientPlayerEntity player = mc.player;
        ClientWorld world = mc.world;
        if (player == null || world == null) {
            return;
        }

        if ((world.getTime() >> 5 & 1) == 0) {
            onTick32();
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
        Vosk.tick();
    }

    public static void onTick32() {
        // auto update check
        if (Configs.Generic.ENABLE_AUTO_UPDATE_CHECK.getBooleanValue()) {
            PlayerMessageSender pms = PlayerMessageSender.getInstance();
            if (NoglerrClient.updateChecker == null && Configs.Generic.ENABLE_AUTO_UPDATE_CHECK.getBooleanValue()) {
                NoglerrClient.updateChecker = new UpdateChecker(Configs.Generic.SILENT_UPDATE_CHECK.getBooleanValue(),
                        "https://api.jawbts.org/version/noglerr");
            } else if (NoglerrClient.updateChecker.isNoticed()) {
                // pass
            } else if (NoglerrClient.updateChecker.getVersionInfo() != null) {
                NoglerrClient.updateChecker.noticed();

                VersionInfo vi = NoglerrClient.updateChecker.getVersionInfo();
                boolean notSilent = !NoglerrClient.updateChecker.isSilent();
                boolean perfect = true;

                if (vi.isOutDated(NoglerrClient.MOD_VERSION)) {
                    perfect = false;
                    NoglerrClient.LOGGER.warn("Current noglerr version out dated.");
                    if (notSilent) pms.add("red", StringUtils.translate("noglerr.info.versionOutDated"));
                }
                if (vi.isNotSafe(NoglerrClient.MOD_VERSION)) {
                    perfect = false;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Current noglerr version not safe.").append(vi.checkUrl == null ? "" :
                            " Please check this url: " + vi.checkUrl);

                    pms.add("red", StringUtils.translate("noglerr.info.versionNotSafe"));
                    if (vi.checkUrl != null) {
                        MutableText text = new LiteralText(StringUtils.translate("noglerr.info.checkUrl"))
                                .setStyle(Style.EMPTY.withColor(TextColor.parse("red")));
                        text.append(new LiteralText(vi.checkUrl.toString()).setStyle(Style.EMPTY.withClickEvent(
                                new ClickEvent(ClickEvent.Action.OPEN_URL, vi.checkUrl.toString())
                        ).withColor(TextColor.parse("blue")).withUnderline(true)));
                        pms.add(text);
                    }

                    if (vi.safeVersionReady()) {
                        sb.append("\nDownLoad latest version at ").append(vi.latestDownloadUrl);

                        if (notSilent) {
                            MutableText text = new LiteralText(StringUtils.translate("noglerr.info.downloadLatestVersion"));
                            text.append(new LiteralText(vi.latestDownloadUrl.toString()).setStyle(Style.EMPTY.withClickEvent(
                                    new ClickEvent(ClickEvent.Action.OPEN_URL, vi.latestDownloadUrl.toString())
                            ).withColor(TextColor.parse("blue")).withUnderline(true)));
                            pms.add(text);
                        }
                    } else {
                        sb.append("\nSafe version not ready yet. Please disable your mod for now.");

                        pms.add(new LiteralText(StringUtils.translate("noglerr.info.safeVersionNotReady")));
                    }
                    NoglerrClient.LOGGER.warn(sb.toString());
                }

                if (perfect) {
                    NoglerrClient.LOGGER.info("Noglerr at latest safe version.");
                    if (notSilent) {
                        pms.add(StringUtils.translate("noglerr.info.versionPerfect"));
                    }
                }
            } else if (NoglerrClient.updateChecker.isFailed()) {
                NoglerrClient.updateChecker.noticed();

                if (!NoglerrClient.updateChecker.isSilent()) {
                    pms.add(new LiteralText(StringUtils.translate("noglerr.info.checkFiled")).setStyle(
                            Style.EMPTY.withColor(TextColor.parse("red"))
                    ));
                }
            }
        }
    }
}
