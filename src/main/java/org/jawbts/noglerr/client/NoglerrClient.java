package org.jawbts.noglerr.client;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.jawbts.noglerr.commands.NoglerrCommand;
import org.jawbts.noglerr.commands.ShowVarCommand;
import org.jawbts.noglerr.commands.VoskCommand;
import org.jawbts.noglerr.config.ConfigHandler;
import org.jawbts.noglerr.event.Callbacks;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.event.InputHandler;
import org.jawbts.noglerr.tweak.var.ScriptVarManager;
import org.jawbts.noglerr.tweak.voice.Vosk;
import org.jawbts.noglerr.util.UpdateChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class NoglerrClient implements ClientModInitializer {
    public static final String MOD_ID = "noglerr";
    public static final String MOD_NAME = "NoGLErr";
    public static final String MOD_BRANCH_ID = "fabric-1.17.x";

    // see ./gradle.properties:mod_version
    // Must be same.
    public static final String MOD_VERSION = "1.0.7";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static UpdateChecker updateChecker = null;

    private static void commandRegister() {
        ShowVarCommand.init();
        VoskCommand.init();
        NoglerrCommand.init();
    }

    @Override
    public void onInitializeClient() {
        commandRegister();

        ConfigManager.getInstance().registerConfigHandler(MOD_ID, new ConfigHandler());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());

        TickHandler.getInstance().registerClientTickHandler(new ClientTickHandler());

        Callbacks.init(MinecraftClient.getInstance());

        // 语音识别初始化
        Vosk.getInstance().init();

        // Script Vars 初始化
        ScriptVarManager.getInstance().reload();
    }
}
