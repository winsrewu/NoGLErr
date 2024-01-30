package org.jawbts.noglerr.tweak.var;

import net.minecraft.entity.Entity;
import org.jawbts.noglerr.client.NoglerrClient;
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
    public static ScriptVarManager getInstance() {
        return INSTANCE;
    }

    private ScriptEngine engine;

    public ScriptEngine getEngine() {
        return engine;
    }

    public void reload() {
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
        return ((Invocable) engine).invokeFunction(
                    name,
                    new org.jawbts.noglerr.tweak.var.javascript.proxy.Entity(entity),
                    new Utils()
                ).toString();
    }
}
