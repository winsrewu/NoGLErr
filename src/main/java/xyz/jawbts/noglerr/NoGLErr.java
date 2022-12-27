package xyz.jawbts.noglerr;

import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Noglerr implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NoGlErr");
    @Override
    public void onInitialize() {
        LOGGER.info("NoGlErr Loded");
    }
}
