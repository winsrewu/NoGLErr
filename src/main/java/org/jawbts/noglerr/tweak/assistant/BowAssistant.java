package org.jawbts.noglerr.tweak.assistant;

import net.minecraft.item.Items;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.util.PlayerMessageSender;

public class BowAssistant extends ProjectileEntityAssistant {
    public BowAssistant(ProjectileEntityAssistantManager manager, int maxIterations, double minAnsDiff) {
        super(manager, maxIterations, minAnsDiff, 3.0);
    }

    @Override
    public void tick() {
        ;
    }

    @Override
    public boolean isEnabled() {
        return Configs.Toggles.BOW_ASSISTANT.getBooleanValue();
    }
}
