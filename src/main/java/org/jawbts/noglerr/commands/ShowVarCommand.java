package org.jawbts.noglerr.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.tweak.var.TargetManager;
import org.jawbts.noglerr.tweak.var.TextManager;
import org.jawbts.noglerr.tweak.var.VarManager;
import org.jawbts.noglerr.util.PlayerMessageSender;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class ShowVarCommand {
    static PlayerMessageSender pms = PlayerMessageSender.getInstance();

    public static void init() {
        ClientCommandManager.DISPATCHER.register(
                literal("showvar")
                        //show help
                        .executes(context -> {
                            context.getSource().getPlayer().sendMessage(
                                    new TranslatableText("noglerr.command.help"), false);
                            return 1;
                        })
                        .then(literal("var")
                                .then(literal("list")
                                        .executes(context -> getVarList(1))
                                        .then(argument("Page", IntegerArgumentType.integer(1))
                                                .executes(context -> getVarList(IntegerArgumentType.getInteger(context, "Page")))
                                        ))
                                .then(literal("add")
                                        .then(argument("Name", StringArgumentType.string())
                                                .then(argument("Value", StringArgumentType.greedyString())
                                                        .executes(context -> setVar(StringArgumentType.getString(context, "Name"),
                                                                StringArgumentType.getString(context, "Value"), false))
                                                )))
                                .then(literal("modify")
                                        .then(argument("Name", StringArgumentType.string())
                                                .then(argument("Value", StringArgumentType.greedyString())
                                                        .executes(context -> setVar(StringArgumentType.getString(context, "Name"),
                                                                StringArgumentType.getString(context, "Value"), true))
                                                )))
                                .then(literal("del")
                                        .then(argument("Name", StringArgumentType.string())
                                                .executes(context -> delVar(StringArgumentType.getString(context, "Name")))
                                        ))
                        )
                        .then(literal("text")
                                .then(literal("list")
                                        .executes(context -> getTextList(1))
                                        .then(argument("Page", IntegerArgumentType.integer(1))
                                                .executes(context -> getTextList(IntegerArgumentType.getInteger(context, "Page")))
                                        ))
                                .then(literal("add")
                                        .then(argument("Name", StringArgumentType.string())
                                                .then(argument("Context", ClientTextArgumentType.text())
                                                        .executes(context -> setText(StringArgumentType.getString(context, "Name"),
                                                                ClientTextArgumentType.getTextArgument(context, "Context"), false))
                                                )))
                                .then(literal("modify")
                                        .then(argument("Name", StringArgumentType.string())
                                                .then(argument("Context", ClientTextArgumentType.text())
                                                        .executes(context -> setText(StringArgumentType.getString(context, "Name"),
                                                                ClientTextArgumentType.getTextArgument(context, "Context"), true))
                                                )))
                                .then(literal("del")
                                        .then(argument("Name", StringArgumentType.string())
                                                .executes(context -> delText(StringArgumentType.getString(context, "Name")))
                                        ))
                        )
                        .then(literal("target")
                                .then(literal("list")
                                        .executes(context -> getTargetList(1))
                                        .then(argument("Page", IntegerArgumentType.integer(1))
                                                .executes(context -> getTargetList(IntegerArgumentType.getInteger(context, "Page")))
                                        ))
                                .then(literal("add")
                                        .then(argument("Name", StringArgumentType.string())
                                                .then(argument("Context", StringArgumentType.greedyString())
                                                        .executes(context -> setTarget(StringArgumentType.getString(context, "Name"),
                                                                StringArgumentType.getString(context, "Context"), false))
                                                )))
                                .then(literal("modify")
                                        .then(argument("Name", StringArgumentType.string())
                                                .then(argument("Context", StringArgumentType.greedyString())
                                                        .executes(context -> setTarget(StringArgumentType.getString(context, "Name"),
                                                                StringArgumentType.getString(context, "Context"), true))
                                                )))
                                .then(literal("del")
                                        .then(argument("Name", StringArgumentType.string())
                                                .executes(context -> delTarget(StringArgumentType.getString(context, "Name")))
                                        ))
                        )
        );
    }

    private static int getVarList(int page) {
        VarManager.getEditor().printDataList(pms, page);
        return 1;
    }

    private static int getTextList(int page) {
        TextManager.getEditor().printDataList(pms, page);
        return 1;
    }

    private static int getTargetList(int page) {
        TargetManager.getEditor().printDataList(pms, page);
        return 1;
    }

    private static int setVar(String name, String value, boolean hard) {
        if (VarManager.getInstance().addData(Utils.escapeString(name), Utils.escapeString(value), hard)) {
            pms.add("noglerr.command.succeed");
        } else {
            pms.add("red", "noglerr.command.nameAlreadyExists");
        }
        return 1;
    }

    private static int setText(String name, Text text, boolean hard) {
        if (TextManager.getInstance().addData(Utils.escapeString(name), Text.Serializer.toJson(text), hard)) {
            pms.add("noglerr.command.succeed");
        } else {
            pms.add("red", "noglerr.command.nameAlreadyExists");
        }
        return 1;
    }

    private static int setTarget(String name, String text, boolean hard) {
        if (TargetManager.getInstance().addData(Utils.escapeString(name), Utils.escapeString(text), hard)) {
            pms.add("noglerr.command.succeed");
        } else {
            pms.add("red", "noglerr.command.nameAlreadyExists");
        }
        return 1;
    }

    private static int delVar(String name) {
        if (VarManager.getInstance().delData(Utils.escapeString(name))) {
            pms.add("noglerr.command.succeed");
        } else {
            pms.add("red", "noglerr.command.nameNotExists");
        }
        return 1;
    }

    private static int delText(String name) {
        if (TextManager.getInstance().delData(Utils.escapeString(name))) {
            pms.add("noglerr.command.succeed");
        } else {
            pms.add("red", "noglerr.command.nameNotExists");
        }
        return 1;
    }

    private static int delTarget(String name) {
        if (TargetManager.getInstance().delData(Utils.escapeString(name))) {
            pms.add("noglerr.command.succeed");
        } else {
            pms.add("red", "noglerr.command.nameNotExists");
        }
        return 1;
    }
}