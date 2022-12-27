package xyz.jawbts.noglerr.commands;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.TranslatableText;
import com.mojang.brigadier.arguments.BoolArgumentType;
import xyz.jawbts.noglerr.StaticValueManager;
import xyz.jawbts.noglerr.Util;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class NoglerrCommand {
    public static void init() {
        ClientCommandManager.DISPATCHER.register(
                literal("noglerr")
                        //show help
                        .executes(context -> {
                            context.getSource().getPlayer().sendMessage(new TranslatableText("command.noglerr.main"), false);
                            return 1;
                        })
                        //ShowIdAboveItem Switcher
                        .then(literal("ShowIdAboveItem")
                                .then(ClientCommandManager.argument("switch", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean flag = BoolArgumentType.getBool(context, "switch");
                                        StaticValueManager.Saver.put("ShowIdAboveItem", flag);
                                        context.getSource().getPlayer().sendMessage(Util.showFunctionStatusBool("ShowIdAboveItem"), false);
                                        return 1;
                                    }))
                                .executes(context -> {
                                    context.getSource().getPlayer().sendMessage(
                                            Util.showFunctionStatusBool("ShowIdAboveItem"), false);
                                    return 1;
                                }))
        );
    }
}