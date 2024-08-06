package org.jawbts.noglerr.commands;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.JsonReaderUtils;

import java.util.Arrays;
import java.util.Collection;

public class ClientTextArgumentType implements ArgumentType<Text> {
    public static final DynamicCommandExceptionType INVALID_COMPONENT_EXCEPTION = new DynamicCommandExceptionType(text -> Text.stringifiedTranslatable("argument.component.invalid", text));
    private static final Collection<String> EXAMPLES = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
    // TODO
    private final RegistryWrapper.WrapperLookup registryLookup = null;

    private ClientTextArgumentType() {
    }

    public static Text getTextArgument(CommandContext<FabricClientCommandSource> context, String name) {
        return context.getArgument(name, Text.class);
    }

    public static ClientTextArgumentType text() {
        return new ClientTextArgumentType();
    }

    public Text parse(StringReader stringReader) throws CommandSyntaxException {
        try {
            return JsonReaderUtils.parse(this.registryLookup, stringReader, TextCodecs.CODEC);
        } catch (Exception exception) {
            String string = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
            throw INVALID_COMPONENT_EXCEPTION.createWithContext(stringReader, string);
        }
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}

