package org.jawbts.noglerr.util;

import net.minecraft.util.Util;
import org.jawbts.noglerr.client.NoglerrClient;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public class ScriptVarUtils {
    private static final File CONFIG_DIR = new File("./config/noglerr/var_scripts");

    public static @Nullable File getConfigDir() {
        if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs())
            return CONFIG_DIR;
        NoglerrClient.LOGGER.error("Error while opening folder. '{}", CONFIG_DIR.getAbsolutePath());
        return null;
    }

    public static File[] getVarFiles() {
        return Objects.requireNonNull(getConfigDir()).listFiles(pathname -> {
            if (!(pathname.isFile() && pathname.canRead())) return false;
            return pathname.getName().endsWith(".js");
        });
    }

    public static boolean openFolder() {
        Util.getOperatingSystem().open(getConfigDir());
        return true;
    }
}
