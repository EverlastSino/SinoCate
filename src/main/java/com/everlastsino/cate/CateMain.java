package com.everlastsino.cate;

import com.everlastsino.cate.api.CateCompostableItemRegister;
import com.everlastsino.cate.api.CateFlammableBlockRegister;
import com.everlastsino.cate.api.CateFuelItemRegister;
import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.item.CateItemTags;
import com.everlastsino.cate.item.CateItems;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.screen.CateScreenHandlers;
import com.everlastsino.cate.util.CateCommands;
import com.everlastsino.cate.util.CateListeners;
import com.everlastsino.cate.world.CateFeatures;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class CateMain implements ModInitializer {
	public static final Logger CateLogger = LogManager.getLogger("SinoCateServer");

	@Override
	public void onInitialize() {
		CateLogger.info("Ready to register objects. From EverlastSino Team ^_^!");
		GeckoLib.initialize();

		CateItems.registerItems();
		CateFuelItemRegister.register();

		CateScreenHandlers.registerScreenHandlers();
		CateBlocks.registerBlocks();
		CateBlockEntities.createBlockEntities();
		CateFlammableBlockRegister.register();
		CateCompostableItemRegister.register();

		CateItemTags.register();

		CateRecipes.registerRecipes();

		CateFeatures.registerFeatures();

		CateSoundEvents.register();

		CateListeners.register();

		CateCommands.register();

		CateLogger.info("Successfully loaded SinoCate Mod based on Fabric.");

	}

}
