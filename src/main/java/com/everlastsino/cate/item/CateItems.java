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
    public static final Item Salt = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Salt_Bucket = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

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
        registerItem("paddy_straw", Paddy_Straw);
        registerItem("osmanthus", Osmanthus);

        //食材
        registerItem("rice", Rice);

        //猎物
        registerItem("oyster", Oyster);

        //调味品
        registerItem("oyster_sauce", Oyster_Sauce);
        registerItem("salt", Salt);
        registerItem("salt_bucket", Salt_Bucket);

        //熟食区
        registerItem("osmanthus_adzuki_bean_porridge", Osmanthus_Adzuki_Bean_Porridge);
        registerItem("cooked_oyster", Cooked_Oyster);

        //刷怪蛋
        registerItem("oyster_spawn_egg", Oyster_Spawn_Egg);

        //方块物品
        //普通方块
        registerItem("saucepan", Saucepan);
        registerItem("wooden_sieve", Wooden_Sieve);

        //草木
        registerItem("osmanthus_tree_sapling", Osmanthus_Tree_Sapling);
        registerItem("osmanthus_tree_leaves", Osmanthus_Tree_Leaves);
        registerItem("osmanthus_tree_log", Osmanthus_Tree_Log);

        //作物
        //耕地
        registerItem("rough_rice", Rough_Rice);
        registerItem("adzuki_bean", Adzuki_Bean);
        registerItem("ginger", Ginger);

        //非耕地
        registerItem("grown_paddy_seedling", Grown_Paddy_Seedling);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier("cate", name), item);
    }

}
