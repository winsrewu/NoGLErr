package org.jawbts.noglerr.event;

import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import org.jawbts.noglerr.NoGLErr;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.config.Hotkeys;


public class InputHandler implements IKeybindProvider, IKeyboardInputHandler {
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
        super();
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : Hotkeys.GENERIC_HOTKEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }

        for (IHotkey hotkey : Configs.Toggles.OPTIONS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(NoGLErr.MOD_NAME, "Gereric", Hotkeys.GENERIC_HOTKEY_LIST);
        manager.addHotkeysForCategory(NoGLErr.MOD_NAME, "Toggles", Configs.Toggles.OPTIONS);
    }
}
