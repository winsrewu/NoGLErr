package org.jawbts.noglerr.client;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import fi.dy.masa.malilib.util.StringUtils;
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
import org.jawbts.noglerr.util.UpdateChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class NoglerrClient implements ClientModInitializer {
    public static final String MOD_ID = "noglerr";
    public static final String MOD_NAME = "NoGLErr";
    public static final String MOD_BRANCH_ID = "fabric-1.17.x";

    public static final String MOD_VERSION = StringUtils.getModVersionString(MOD_ID);

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

        // 直接读取config, 去你的malilib(它会自动读取), 我看过代码了应该不会出问题, 没有一定要在mc加载完后再加载
        ConfigHandler.loadFile();

        // Script Vars 初始化
        ScriptVarManager.getInstance().reload();
    }
}
