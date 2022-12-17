package xyz.jawbts.noglerr;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoGLErr implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("noglerr");

	@Override
	public void onInitialize() {
		LOGGER.info("NOGLErr loded");
	}

	public static final GameRules.Key<GameRules.BooleanRule> NGE_SHOW_DEBUG_ON_ITEM_NAME =
			GameRuleRegistry.register("nge:ShowDebugOnItemName", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
	public static final GameRules.Key<GameRules.BooleanRule> NGE_SHOW_DEBUG_ON_ENTITY_NAME =
			GameRuleRegistry.register("nge:ShowDebugOnEntityName", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
}
