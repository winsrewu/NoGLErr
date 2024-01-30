package org.jawbts.noglerr.commands;

import fi.dy.masa.malilib.util.StringUtils;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.TranslatableText;
import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.util.PlayerMessageSender;
import org.jawbts.noglerr.util.UpdateChecker;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class NoglerrCommand {
    static PlayerMessageSender pms = PlayerMessageSender.getInstance();

    public static void init() {
        ClientCommandManager.DISPATCHER.register(literal("noglerr")
                //show help
                .executes(context -> {
                    context.getSource().getPlayer().sendMessage(
                            new TranslatableText("noglerr.command.help"), false);
                    return 1;
                })
                // show current version
                .then(literal("version")
                        .executes(context -> showVersion())
                        .then(literal("check").executes(context -> {
                            NoglerrClient.updateChecker = new UpdateChecker(false, "https://api.jawbts.org/version/noglerr");
                            return 1;
                        }))
                )
        );
    }

    private static int showVersion() {
        if (NoglerrClient.updateChecker.getVersionInfo() == null || NoglerrClient.updateChecker.isFailed()) {
            pms.add(StringUtils.translate("noglerr.command.versionInfoNotReady"));
        } else {
            pms.add(StringUtils.translate("noglerr.command.versionInfo", NoglerrClient.MOD_BRANCH_ID,
                    NoglerrClient.MOD_VERSION, NoglerrClient.updateChecker.getVersionInfo().latestVersion,
                    NoglerrClient.updateChecker.getVersionInfo().lowestSafeVersion));
        }
        return 1;
    }
}
