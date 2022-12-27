package xyz.jawbts.noglerr.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import xyz.jawbts.noglerr.commands.NoglerrCommand;

@Environment(EnvType.CLIENT)
public class NoglerrClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        commandRegister();
    }

    public static void commandRegister() {
        NoglerrCommand.init();
    }
}
