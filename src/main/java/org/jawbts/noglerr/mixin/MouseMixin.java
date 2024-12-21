package org.jawbts.noglerr.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.tweak.assistant.ProjectileEntityAssistantManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow private double cursorDeltaX;
    @Shadow private double cursorDeltaY;

    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void updateMouse(CallbackInfo ci) {
        if (!ProjectileEntityAssistantManager.getInstance().isCaptured() || client.player == null) return;
        client.player.setYaw(ProjectileEntityAssistantManager.getInstance().getTargetYaw());
        client.player.setPitch((float) (client.player.getPitch() +
                        Utils.toLowestModifyDegree(ProjectileEntityAssistantManager.getInstance().getTargetPitch() - client.player.getPitch() % 360)));

        cursorDeltaX = 0;
        cursorDeltaY = 0;
        ci.cancel();
    }
}
