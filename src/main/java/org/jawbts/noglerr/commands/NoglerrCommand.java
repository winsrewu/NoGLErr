package org.jawbts.noglerr.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import fi.dy.masa.malilib.util.StringUtils;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.TranslatableText;
import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.util.PlayerMessageSender;
import org.jawbts.noglerr.util.UpdateChecker;
import org.jawbts.noglerr.util.Tester;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;

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
                // test
                .then(literal("test")
                        .executes(context -> {
                            pms.add(StringUtils.translate("noglerr.info.testCommand"));
                            return 1;
                        })
                        .then(argument("key", StringArgumentType.greedyString()).executes(context -> {
                            // md5
                            // well, if u really want to use this
                            // a2V5OiA5NzIzMDA=
                            // DO NOT use it in production!
                            String key = StringArgumentType.getString(context, "key");
                            try {
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                md.update(key.getBytes());
                                byte[] digest = md.digest();
                                StringBuilder sb = new StringBuilder();
                                for (byte b : digest) {
                                    sb.append(String.format("%02x", b));
                                }
                                if (sb.toString().equals("c0479da5d4aa2316fb2bb940eff1f302")) {
                                    return Tester.runAll() ? 1 : 0;
                                } else {
                                    pms.add(StringUtils.translate("noglerr.info.testCommand"));
                                }
                                return 1;
                            } catch (NoSuchAlgorithmException e) {
                                NoglerrClient.LOGGER.error("MD5 algorithm not found.", e);
                                return 0;
                            }
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
