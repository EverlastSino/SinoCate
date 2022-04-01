package com.everlastsino.cate.item;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.item.items.RollingPinItem;
import com.everlastsino.cate.item.items.ItemWithToolTip;
import com.everlastsino.cate.item.items.SimpleDrinkItem;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class CateItems {
    //普通物品
    //工具
    public static final Item Rolling_Pin = new RollingPinItem(
            new FabricItemSettings().group(CateItemGroups.CateGroup).maxCount(1).maxDamage(128));
    public static final Item Kitchen_Knife = new SwordItem(ToolMaterials.IRON, 2, -3.0f,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //农副产品
    public static final Item Paddy_Straw = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Osmanthus = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //食材
    public static final Item Rice = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Flour = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Dough = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Tang_Yuan = new ItemWithToolTip("tang_yuan", true,
            new FabricItemSettings().group(CateItemGroups.CateGroup), Formatting.AQUA, Formatting.ITALIC);

    //猎物
    public static final Item Oyster = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //调味品
    public static final Item Oyster_Sauce = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Salt = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Salt_Bucket = new Item(new FabricItemSettings()
            .recipeRemainder(Items.BUCKET).group(CateItemGroups.CateGroup));

    //生食区
    public static final Item Raw_Soymilk = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //熟食区
    public static final Item Osmanthus_Adzuki_Bean_Porridge = new StewItem(
            new FabricItemSettings().group(CateItemGroups.CateGroup).food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(2.0F).build()));
    public static final Item Cooked_Oyster = new Item(
            new FabricItemSettings().group(CateItemGroups.CateGroup).food(FoodComponents.COOKED_SALMON));
    public static final Item Soymilk = new SimpleDrinkItem(Items.GLASS_BOTTLE,
            new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE)
                    .group(CateItemGroups.CateGroup).maxCount(16));

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


    //草木
    public static final BlockItem Osmanthus_Tree_Sapling = new BlockItem(CateBlocks.Osmanthus_Tree_Sapling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Leaves = new BlockItem(CateBlocks.Osmanthus_Tree_Leaves,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Log = new BlockItem(CateBlocks.Osmanthus_tree_Log,
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

    //非耕地
    public static final BlockItem Grown_Paddy_Seedling = new BlockItem(CateCrops.Paddy,
            new FabricItemSettings().group(CateItemGroups.CateGroup));


    public static void registerItems(){
        //普通物品
        //工具
        registerItem("rolling_pin", Rolling_Pin);
        registerItem("kitchen_knife", Kitchen_Knife);

        //农副产品
        registerItem("paddy_straw", Paddy_Straw);
        registerItem("osmanthus", Osmanthus);

        //食材
        registerItem("rice", Rice);
        registerItem("flour", Flour);
        registerItem("dough", Dough);
        registerItem("tang_yuan", Tang_Yuan);

        //猎物
        registerItem("oyster", Oyster);

        //调味品
        registerItem("oyster_sauce", Oyster_Sauce);
        registerItem("salt", Salt);
        registerItem("salt_bucket", Salt_Bucket);

        //生食区
        registerItem("raw_soymilk", Raw_Soymilk);

        //熟食区
        registerItem("osmanthus_adzuki_bean_porridge", Osmanthus_Adzuki_Bean_Porridge);
        registerItem("cooked_oyster", Cooked_Oyster);
        registerItem("soymilk", Soymilk);

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

        //草木
        registerItem("osmanthus_tree_sapling", Osmanthus_Tree_Sapling);
        registerItem("osmanthus_tree_leaves", Osmanthus_Tree_Leaves);
        registerItem("osmanthus_tree_log", Osmanthus_Tree_Log);

        //矿石
        registerItem("rock_salt_ore", Rock_Salt_Ore);

        //作物
        //耕地
        registerItem("rough_rice", Rough_Rice);
        registerItem("adzuki_bean", Adzuki_Bean);
        registerItem("ginger", Ginger);
        registerItem("soybean", Soybean);

        //非耕地
        registerItem("grown_paddy_seedling", Grown_Paddy_Seedling);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier("cate", name), item);
    }
}
