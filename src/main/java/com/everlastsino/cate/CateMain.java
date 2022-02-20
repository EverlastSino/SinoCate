package com.everlastsino.cate;

import com.everlastsino.cate.api.CateFlammableBlockRegister;
import com.everlastsino.cate.api.CateFuelItemRegister;
import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.item.CateItems;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.screen.CateScreenHandlers;
import com.everlastsino.cate.world.CateFeatures;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CateMain implements ModInitializer {
	public static final Logger CateLogger = LogManager.getLogger("SinoCateServer");

	@Override
	public void onInitialize() {
		CateLogger.info("Ready to register objects. Best wishes from EverlastSino Team ^_^!");

		CateCrops.registerCropBlocks();
		CateScreenHandlers.registerScreenHandlers();
		CateBlocks.registerBlocks();
		CateBlockEntities.createBlockEntities();
		CateFlammableBlockRegister.register();

		CateItems.registerItems();
		CateFuelItemRegister.register();

		CateEntities.registerEntities();

		CateRecipes.registerRecipes();

		CateFeatures.registerFeatures();

		CateLogger.info("Successfully loaded SinoCate Mod for fabric.");

	}

}
