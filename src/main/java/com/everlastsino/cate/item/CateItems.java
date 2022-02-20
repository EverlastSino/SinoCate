package com.everlastsino.cate.item;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateItems {
    //普通物品
    //农副产品
    public static final Item Paddy_Straw = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Osmanthus = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //食材
    public static final Item Rice = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //猎物
    public static final Item Oyster = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //调味品
    public static final Item Oyster_Sauce = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //熟食区
    public static final Item Osmanthus_Adzuki_Bean_Porridge = new StewItem(
            new FabricItemSettings().group(CateItemGroups.CateGroup).food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(2.0F).build()));
    public static final Item Cooked_Oyster = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKED_SALMON));

    //刷怪蛋
    public static final Item Oyster_Spawn_Egg = new SpawnEggItem(CateEntities.Oyster_Entity,
            0x4f6a74, 0xbdbfa0, new FabricItemSettings().group(CateItemGroups.CateGroup));

    //方块物品
    //普通方块
    public static final BlockItem Saucepan = new BlockItem(CateBlocks.Saucepan,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Wooden_Sieve = new BlockItem(CateBlocks.Wooden_Sieve,
            new FabricItemSettings().group(CateItemGroups.CateGroup));


    //草木
    public static final BlockItem Osmanthus_Tree_Sapling = new BlockItem(CateBlocks.Osmanthus_Tree_Sapling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Leaves = new BlockItem(CateBlocks.Osmanthus_Tree_Leaves,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Log = new BlockItem(CateBlocks.Osmanthus_tree_Log,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //作物
    //耕地
    public static final BlockItem Rough_Rice = new BlockItem(CateCrops.Paddy_Seedling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Adzuki_Bean = new BlockItem(CateCrops.Adzuki_Bean,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Ginger = new BlockItem(CateCrops.Ginger,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //非耕地
    public static final BlockItem Grown_Paddy_Seedling = new BlockItem(CateCrops.Paddy,
            new FabricItemSettings().group(CateItemGroups.CateGroup));


    public static void registerItems(){
        //普通物品
        //农副产品
        Registry.register(Registry.ITEM, new Identifier("cate", "paddy_straw"), Paddy_Straw);
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus"), Osmanthus);

        //食材
        Registry.register(Registry.ITEM, new Identifier("cate", "rice"), Rice);

        //猎物
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster"), Oyster);

        //调味品
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster_sauce"), Oyster_Sauce);

        //熟食区
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus_adzuki_bean_porridge"), Osmanthus_Adzuki_Bean_Porridge);
        Registry.register(Registry.ITEM, new Identifier("cate", "cooked_oyster"), Cooked_Oyster);

        //刷怪蛋
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster_spawn_egg"), Oyster_Spawn_Egg);

        //方块物品
        //普通方块
        Registry.register(Registry.ITEM, new Identifier("cate", "saucepan"), Saucepan);
        Registry.register(Registry.ITEM, new Identifier("cate", "wooden_sieve"), Wooden_Sieve);

        //草木
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus_tree_sapling"), Osmanthus_Tree_Sapling);
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus_tree_leaves"), Osmanthus_Tree_Leaves);
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus_tree_log"), Osmanthus_Tree_Log);

        //作物
        //耕地
        Registry.register(Registry.ITEM, new Identifier("cate", "rough_rice"), Rough_Rice);
        Registry.register(Registry.ITEM, new Identifier("cate", "adzuki_bean"), Adzuki_Bean);
        Registry.register(Registry.ITEM, new Identifier("cate", "ginger"), Ginger);

        //非耕地
        Registry.register(Registry.ITEM, new Identifier("cate", "grown_paddy_seedling"), Grown_Paddy_Seedling);
    }

}
