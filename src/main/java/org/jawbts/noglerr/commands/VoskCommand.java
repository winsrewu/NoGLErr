package org.jawbts.noglerr.commands;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.TranslatableText;
import org.jawbts.noglerr.tweak.voice.Vosk;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class VoskCommand {

    public static void init() {
        ClientCommandManager.DISPATCHER.register(literal("vosk")
                //show help
                .executes(context -> {
                    context.getSource().getPlayer().sendMessage(
                            new TranslatableText("noglerr.command.help"), false);
                    return 1;
                })
                //重新加载模型
                .then(literal("reload")
                        .executes(context -> {
                            Vosk.getInstance().reload();
                            return 1;
                        })
                )
        );
    }
}
