package org.jawbts.noglerr.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import org.jawbts.noglerr.screen.ConfigScreen;

public class ModMenuApiImpl implements ModMenuApi {
    static Screen createModScreen(Screen previous) {
        ConfigScreen screen = new ConfigScreen();
        screen.setParentGui(previous);
        return screen;
    }

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return ModMenuApiImpl::createModScreen;
    }
}
