package org.jawbts.noglerr.tweak.var;

import net.minecraft.entity.Entity;
import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.tweak.var.javascript.Utils;
import org.jawbts.noglerr.util.PlayerMessageSender;
import org.jawbts.noglerr.util.ScriptVarUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScriptVarManager {
    private static final ScriptVarManager INSTANCE = new ScriptVarManager();
    private static final PlayerMessageSender pms = PlayerMessageSender.getInstance();
    private ScriptEngine engine;

    public static ScriptVarManager getInstance() {
        return INSTANCE;
    }

    public ScriptEngine getEngine() {
        return engine;
    }

    public void reload() {
        if (!Configs.Generic.ENABLE_NASHORN_ENGINE.getBooleanValue()) {
            pms.add("Nashorn engine is disabled.");
            return;
        }

        engine = new ScriptEngineManager().getEngineByName("nashorn");
        for (File f : ScriptVarUtils.getVarFiles()) {
            try {
                engine.eval(new FileReader(f));
            } catch (FileNotFoundException ignored) {

            } catch (ScriptException e) {
                pms.add(e.getMessage());
                NoglerrClient.LOGGER.error("Script Exception. Player Script.", e);
            }
        }
    }

    public ScriptVarDataHandler getHandler(String name) {
        return new ScriptVarDataHandler(name);
    }

    public String getRes(String name, Entity entity) throws ScriptException, NoSuchMethodException {
        if (!Configs.Generic.ENABLE_NASHORN_ENGINE.getBooleanValue()) {
            pms.add("Nashorn engine is disabled.");
            return "[Nashorn engine is disabled.]";
        }

        return ((Invocable) engine).invokeFunction(
                name,
                entity,
                new Utils()
        ).toString();
    }

    public String callFunction(String name, Object... args) {
        if (!Configs.Generic.ENABLE_NASHORN_ENGINE.getBooleanValue()) {
            pms.add("Nashorn engine is disabled.");
            return "[Nashorn engine is disabled.]";
        }
        try {
            ((Invocable) engine).invokeFunction(
                    name,
                    ClientTickHandler.player,
                    new Utils(),
                    args
            );
            return "Success";
        } catch (ScriptException e) {
            return e.getMessage();
        } catch (NoSuchMethodException e) {
            return "noglerr.command.nameNotExists";
        }
    }
}
