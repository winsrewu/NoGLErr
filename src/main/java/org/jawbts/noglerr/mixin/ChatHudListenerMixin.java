package org.jawbts.noglerr.mixin;

import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jawbts.noglerr.util.PlayerMessageSender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.UUID;

@Mixin(ChatHudListener.class)
public class ChatHudListenerMixin {
    @Inject(method = "onChatMessage(Lnet/minecraft/network/MessageType;Lnet/minecraft/text/Text;Ljava/util/UUID;)V",
            at = @At("HEAD"), cancellable = true)
    public void onChatMessage(MessageType messageType, Text message, UUID sender, CallbackInfo ci) {
        // 只捕获私信
        if (messageType != MessageType.SYSTEM || !(message instanceof TranslatableText)) {
            return;
        }
        if (!((TranslatableText) message).getKey().equals("commands.message.display.incoming")) {
            return;
        }
        Object args[] = ((TranslatableText) message).getArgs();
        if (args.length != 2 || !(args[0] instanceof LiteralText) || !(args[1] instanceof LiteralText)) {
            return;
        }

        PlayerMessageSender.getInstance().add("GET IT");
        PlayerMessageSender.getInstance().add("Sender: " + ((LiteralText) args[0]).getString() +
                " CONTEXT: " + ((LiteralText) args[1]).getString());
        ci.cancel();
    }
}
