package com.everlastsino.cate.item;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.item.items.ItemWithToolTip;
import com.everlastsino.cate.item.items.SimpleDrinkItem;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateItems {
    //普通物品
    //工具
    public static final Item Rolling_Pin = new Item(
            new FabricItemSettings().group(CateItemGroups.CateGroup).maxCount(1).maxDamage(128));
    public static final Item Kitchen_Knife = new SwordItem(ToolMaterials.IRON, 2, -3.0f,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //农副产品
    public static final Item Paddy_Straw = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Osmanthus = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Orange = new Item(new FabricItemSettings()
            .group(CateItemGroups.CateGroup).food(FoodComponents.APPLE));
    public static final Item Garlic_Leaf = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Garlic_Bolt = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //食材
    public static final Item Rice = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Flour = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Minced_Meat = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //面制品
    public static final Item Dough = new Item(new FabricItemSettings().group(CateItemGroups.Flour_Products));
    public static final Item Dough_Bar = new Item(new FabricItemSettings().group(CateItemGroups.Flour_Products));
    public static final Item Tangyuan = new ItemWithToolTip("tangyuan", true,
            new FabricItemSettings().group(CateItemGroups.Flour_Products), Formatting.AQUA, Formatting.ITALIC);
    public static final Item Dough_Jizi = new ItemWithToolTip("dough_jizi", true,
            new FabricItemSettings().group(CateItemGroups.Flour_Products), Formatting.AQUA, Formatting.ITALIC);
    public static final Item Wheat_Noodles = new Item(new FabricItemSettings().group(CateItemGroups.Flour_Products));
    public static final Item Dough_Piece = new Item(new FabricItemSettings().group(CateItemGroups.Flour_Products));
    public static final Item Dumpling = new Item(new FabricItemSettings().group(CateItemGroups.Flour_Products));
    public static final Item Leavened_Dough = new Item(new FabricItemSettings().group(CateItemGroups.Flour_Products));
    public static final Item Mantou = new ItemWithToolTip("mantou", true,
            new FabricItemSettings().group(CateItemGroups.Flour_Products), Formatting.AQUA, Formatting.ITALIC);

    //猎物
    public static final Item Oyster = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //调味品
    public static final Item Oyster_Sauce = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Salt = new Item(new FabricItemSettings()
            .group(CateItemGroups.CateGroup).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item Salt_Bucket = new Item(new FabricItemSettings()
            .recipeRemainder(Items.BUCKET).group(CateItemGroups.CateGroup));

    //生食区
    public static final Item Raw_Soymilk = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Green_Soybean = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Baozi = new ItemWithToolTip("baozi", true,
            new FabricItemSettings().group(CateItemGroups.CateGroup), Formatting.AQUA, Formatting.ITALIC);

    //熟食区
    public static final Item Osmanthus_Adzuki_Bean_Porridge = new StewItem(
            new FabricItemSettings().group(CateItemGroups.CateGroup).food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(2.0f).build()));
    public static final Item Cooked_Oyster = new Item(new FabricItemSettings()
            .group(CateItemGroups.CateGroup).food(FoodComponents.COOKED_SALMON));
    public static final Item Soymilk = new SimpleDrinkItem(Items.GLASS_BOTTLE,
            new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE)
                    .group(CateItemGroups.CateGroup).maxCount(16));
    public static final Item Cooked_Green_Soybean = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup)
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build()));
    public static final Item Cooked_Baozi = new ItemWithToolTip("cooked_baozi", true,
            new FabricItemSettings().group(CateItemGroups.CateGroup)
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(1.6f).meat().build()), Formatting.AQUA, Formatting.ITALIC);
    public static final Item Cured_Porkchop = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Smoked_Porkchop = new Item(new FabricItemSettings()
            .group(CateItemGroups.CateGroup).food(FoodComponents.COOKED_PORKCHOP));

    //刷怪蛋
    public static final Item Oyster_Spawn_Egg = new SpawnEggItem(CateEntities.Oyster_Entity,
            0x4f6a74, 0xbdbfa0, new FabricItemSettings().group(CateItemGroups.CateGroup));

    //方块物品
    //装饰方块
    public static final BlockItem Salt_Block = new BlockItem(CateBlocks.Salt_Block,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //功能方块
    public static final BlockItem Saucepan = new BlockItem(CateBlocks.Saucepan,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Wooden_Sieve = new BlockItem(CateBlocks.Wooden_Sieve,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Evaporation_Pan = new BlockItem(CateBlocks.Evaporation_Pan,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Cutting_Board = new BlockItem(CateBlocks.Cutting_Board,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Stone_Mill = new BlockItem(CateBlocks.Stone_Mill,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Wooden_Steamer = new BlockItem(CateBlocks.Wooden_Steamer,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Smoking_Rack = new BlockItem(CateBlocks.Smoking_Rack,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Sun_Drying_Tray = new BlockItem(CateBlocks.Sun_Drying_Tray,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //草木
    public static final BlockItem Osmanthus_Tree_Sapling = new BlockItem(CateBlocks.Osmanthus_Tree_Sapling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Leaves = new BlockItem(CateBlocks.Osmanthus_Tree_Leaves,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Log = new BlockItem(CateBlocks.Osmanthus_Tree_Log,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Orange_Tree_Sapling = new BlockItem(CateBlocks.Orange_Tree_Sapling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Orange_Tree_Leaves = new BlockItem(CateBlocks.Orange_Tree_Leaves,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //矿石
    public static final BlockItem Rock_Salt_Ore = new BlockItem(CateBlocks.Rock_Salt_Ore,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //作物
    //耕地
    public static final BlockItem Rough_Rice = new BlockItem(CateCrops.Paddy_Seedling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Adzuki_Bean = new BlockItem(CateCrops.Adzuki_Bean,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Ginger = new BlockItem(CateCrops.Ginger,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Soybean = new BlockItem(CateCrops.Soybean,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Garlic = new BlockItem(CateCrops.Garlic,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //非耕地
    public static final BlockItem Grown_Paddy_Seedling = new BlockItem(CateCrops.Paddy,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //Easter Eggs
    public static final Item Baguette = new SwordItem(ToolMaterials.IRON, 2, -2.0f,
            new FabricItemSettings().maxDamage(64).food(new FoodComponent.Builder().hunger(8).saturationModifier(1f).build()));


    public static void registerItems(){
        //普通物品
        //工具
        registerItem("rolling_pin", Rolling_Pin);
        registerItem("kitchen_knife", Kitchen_Knife);

        //农副产品
        registerItem("paddy_straw", Paddy_Straw);
        registerItem("osmanthus", Osmanthus);
        registerItem("orange", Orange);
        registerItem("garlic_leaf", Garlic_Leaf);
        registerItem("garlic_bolt", Garlic_Bolt);

        //食材
        registerItem("rice", Rice);
        registerItem("flour", Flour);
        registerItem("minced_meat", Minced_Meat);

        //面制品
        registerItem("dough", Dough);
        registerItem("dough_bar", Dough_Bar);
        registerItem("tangyuan", Tangyuan);
        registerItem("dough_jizi", Dough_Jizi);
        registerItem("wheat_noodles", Wheat_Noodles);
        registerItem("dough_piece", Dough_Piece);
        registerItem("dumpling", Dumpling);
        registerItem("leavened_dough", Leavened_Dough);
        registerItem("mantou", Mantou);

        //猎物
        registerItem("oyster", Oyster);

        //调味品
        registerItem("oyster_sauce", Oyster_Sauce);
        registerItem("salt", Salt);
        registerItem("salt_bucket", Salt_Bucket);

        //生食区
        registerItem("raw_soymilk", Raw_Soymilk);
        registerItem("green_soybean", Green_Soybean);
        registerItem("baozi", Baozi);

        //熟食区
        registerItem("osmanthus_adzuki_bean_porridge", Osmanthus_Adzuki_Bean_Porridge);
        registerItem("cooked_oyster", Cooked_Oyster);
        registerItem("soymilk", Soymilk);
        registerItem("cooked_green_soybean", Cooked_Green_Soybean);
        registerItem("cooked_baozi", Cooked_Baozi);
        registerItem("cured_porkchop", Cured_Porkchop);
        registerItem("smoked_porkchop", Smoked_Porkchop);

        //刷怪蛋
        registerItem("oyster_spawn_egg", Oyster_Spawn_Egg);

        //方块物品
        //装饰方块
        registerItem("salt_block", Salt_Block);

        //功能方块
        registerItem("saucepan", Saucepan);
        registerItem("wooden_sieve", Wooden_Sieve);
        registerItem("evaporation_pan", Evaporation_Pan);
        registerItem("cutting_board", Cutting_Board);
        registerItem("stone_mill", Stone_Mill);
        registerItem("wooden_steamer", Wooden_Steamer);
        registerItem("smoking_rack", Smoking_Rack);
        registerItem("sun_drying_tray", Sun_Drying_Tray);

        //草木
        registerItem("osmanthus_tree_sapling", Osmanthus_Tree_Sapling);
        registerItem("osmanthus_tree_leaves", Osmanthus_Tree_Leaves);
        registerItem("osmanthus_tree_log", Osmanthus_Tree_Log);
        registerItem("orange_tree_sapling", Orange_Tree_Sapling);
        registerItem("orange_tree_leaves", Orange_Tree_Leaves);

        //矿石
        registerItem("rock_salt_ore", Rock_Salt_Ore);

        //作物
        //耕地
        registerItem("rough_rice", Rough_Rice);
        registerItem("adzuki_bean", Adzuki_Bean);
        registerItem("ginger", Ginger);
        registerItem("soybean", Soybean);
        registerItem("garlic", Garlic);

        //非耕地
        registerItem("grown_paddy_seedling", Grown_Paddy_Seedling);

        //Easter Eggs
        registerItem("baguette", Baguette);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier("cate", name), item);
    }
}
