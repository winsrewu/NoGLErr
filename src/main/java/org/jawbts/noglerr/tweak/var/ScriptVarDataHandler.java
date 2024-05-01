package org.jawbts.noglerr.tweak.var;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jawbts.noglerr.client.NoglerrClient;

import java.util.List;

public class ScriptVarDataHandler extends VarDataHandler {
    private final String name;

    public ScriptVarDataHandler(String name) {
        this.name = name;
    }

    public ScriptVarDataHandler() {
        throw new UnsupportedOperationException();
    }

    public ScriptVarDataHandler(String name, String value) {
        throw new UnsupportedOperationException();
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

    @Override
    public String getData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setData(String data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SavedData getSavedData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setArgList(List<VarDataHandler.Arg> argList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createArgList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}
