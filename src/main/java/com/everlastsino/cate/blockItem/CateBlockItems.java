package com.everlastsino.cate.blockItem;

import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateBlockItems {

    //作物
    public static final BlockItem Paddy = new BlockItem(CateCrops.Paddy, new FabricItemSettings().group(CateItemGroups.CateGroup));


    public static void registerBlockItems(){
        Registry.register(Registry.ITEM, new Identifier("cate", "paddy"), Paddy);
    }


}
