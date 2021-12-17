package com.everlastsino.cate;

import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.item.CateItems;
import net.fabricmc.api.ModInitializer;

public class CateMain implements ModInitializer {

	@Override
	public void onInitialize() {
		CateCrops.registerCropBlocks();
		CateItems.registerItems();
	}

}
