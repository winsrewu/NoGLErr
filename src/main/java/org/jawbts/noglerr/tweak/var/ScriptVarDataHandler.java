package org.jawbts.noglerr.tweak.var;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jawbts.noglerr.client.NoglerrClient;

public class ScriptVarDataHandler extends VarDataHandler {
    private final String name;
    public ScriptVarDataHandler(String name) {
        this.name = name;
    }

    @Override
    public String getTreatedData(Entity entity, ClientWorld world, ClientPlayerEntity player) {
        try {
            return ScriptVarManager.getInstance().getRes(name, entity);
        } catch (Exception e) {
            NoglerrClient.LOGGER.error("Script Error. Player Script {}.", name);
            return "EXCEPTION " + e.getMessage();
        }
    }
}
