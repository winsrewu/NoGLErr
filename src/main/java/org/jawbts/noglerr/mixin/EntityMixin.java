package org.jawbts.noglerr.mixin;

import net.minecraft.entity.Entity;
import org.jawbts.noglerr.tweak.assistant.ProjectileEntityAssistantManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("HEAD"), method = "isGlowing", cancellable = true)
    private void isGlowing(CallbackInfoReturnable<Boolean> info) {
        if (((Object) this) == ProjectileEntityAssistantManager.getInstance().getTargetEntityShower()) {
            info.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getTeamColorValue", cancellable = true)
    private void getTeamColorValue(CallbackInfoReturnable<Integer> info) {
        if (((Object) this) == ProjectileEntityAssistantManager.getInstance().getTargetEntityShower()) {
            info.setReturnValue(0xFF0000);
        }
    }
}
