package org.jawbts.noglerr.tweak.assistant;

import net.minecraft.util.math.Vec3d;
import org.jawbts.noglerr.config.Configs;

import java.util.Optional;

public class EnderPearlAssistant extends ProjectileEntityAssistant {
    public EnderPearlAssistant(ProjectileEntityAssistantManager manager, int maxIterations, double minAnsDiff) {
        super(manager, maxIterations, minAnsDiff, 1.5);
    }

    @Override
    public boolean isEnabled() {
        return Configs.Toggles.BOW_ASSISTANT.getBooleanValue();
    }

    @Override
    public void tick() {
        ;
    }

    @Override
    public Optional<Vec3d> processTargetPos(Vec3d targetPos) {
        if (targetPos == null) return Optional.empty();
        return Optional.of(targetPos.add(0, 2.7, 0));
    }
}
