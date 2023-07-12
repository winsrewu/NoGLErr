package org.jawbts.noglerr.client;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.jawbts.noglerr.NoGLErr;
import org.jawbts.noglerr.commands.ShowVarCommand;
import org.jawbts.noglerr.config.ConfigHandler;
import org.jawbts.noglerr.event.Callbacks;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.event.InputHandler;


@Environment(EnvType.CLIENT)
public class NoglerrClient implements ClientModInitializer {
    private static void commandRegister() {
        ShowVarCommand.init();
    }

    @Override
    public void onInitializeClient() {
        commandRegister();

        ConfigManager.getInstance().registerConfigHandler(NoGLErr.MOD_ID, new ConfigHandler());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());

        TickHandler.getInstance().registerClientTickHandler(new ClientTickHandler());

        Callbacks.init(MinecraftClient.getInstance());
    }
}
