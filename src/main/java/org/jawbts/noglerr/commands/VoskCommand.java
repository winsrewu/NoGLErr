package org.jawbts.noglerr.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import org.jawbts.noglerr.tweak.voice.Vosk;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class VoskCommand {

    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(literal("vosk")
                    //show help
                    .executes(context -> {
                        context.getSource().getPlayer().sendMessage(
                                Text.stringifiedTranslatable("noglerr.command.help"), false);
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
        });
    }
}
