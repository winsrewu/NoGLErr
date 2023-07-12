package org.jawbts.noglerr.event;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;
import org.jawbts.noglerr.config.Hotkeys;
import org.jawbts.noglerr.screen.ConfigScreen;

public class Callbacks {
    public static void init(MinecraftClient mc) {
        IHotkeyCallback callbackGeneric = new KeyCallbackHotkeysGeneric(mc);

        Hotkeys.MENU_OPEN_KEY.getKeybind().setCallback(callbackGeneric);
    }

    private static class KeyCallbackHotkeysGeneric implements IHotkeyCallback {
        private final MinecraftClient mc;

        public KeyCallbackHotkeysGeneric(MinecraftClient mc) {
            this.mc = mc;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == Hotkeys.MENU_OPEN_KEY.getKeybind()) {
                GuiBase.openGui(new ConfigScreen());
                return true;
            }

            return false;
        }
    }
}
