package com.everlastsino.cate;

import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.blockItem.CateBlockItems;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CateMain implements ModInitializer {

	@Override
	public void onInitialize() {
		CateCrops.registerCropBlocks();
		CateBlockItems.registerBlockItems();
	}

}
