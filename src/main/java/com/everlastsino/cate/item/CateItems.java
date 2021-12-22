package com.everlastsino.cate.item;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateItems {
    //普通物品
    //农副产品
    public static final Item Paddy_Straw = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //猎物
    public static final Item Oyster = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //调味品
    public static final Item Oyster_Sauce = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //方块物品
    //普通方块
    public static final BlockItem Saucepan = new BlockItem(CateBlocks.Saucepan,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //作物
    //耕地
    public static final BlockItem Rough_Rice = new BlockItem(CateCrops.Paddy_Seedling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //非耕地
    public static final BlockItem Grown_Paddy_Seedling = new BlockItem(CateCrops.Paddy,
            new FabricItemSettings().group(CateItemGroups.CateGroup));


    public static void registerItems(){
        //普通物品
        //农副产品
        Registry.register(Registry.ITEM, new Identifier("cate", "paddy_straw"), Paddy_Straw);

        //猎物
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster"), Oyster);

        //调味品
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster_sauce"), Oyster_Sauce);

        //方块物品
        //普通方块
        Registry.register(Registry.ITEM, new Identifier("cate", "saucepan"), Saucepan);

        //作物
        //耕地
        Registry.register(Registry.ITEM, new Identifier("cate", "rough_rice"), Rough_Rice);

        //非耕地
        Registry.register(Registry.ITEM, new Identifier("cate", "grown_paddy_seedling"), Grown_Paddy_Seedling);
    }

}
