package xyz.jawbts.noglerr;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoGLErr implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("noglerr");

	@Override
	public void onInitialize() {
		LOGGER.info("NOGLErr loded");
	}
}
