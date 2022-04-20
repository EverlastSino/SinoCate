package com.everlastsino.cate.block;

import com.everlastsino.cate.block.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class CateBlocks {

    private static PillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new PillarBlock(FabricBlockSettings.of(Material.WOOD,
                state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0f).sounds(BlockSoundGroup.WOOD));
    }

    //装饰方块
    public static final Block Salt_Block = new FallingBlock(
            FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).sounds(BlockSoundGroup.SAND));

    //功能方块
    public static final Block Saucepan = new SaucepanBlock(
            FabricBlockSettings.of(Material.METAL).strength(5.0f, 3.0f).sounds(BlockSoundGroup.STONE));
    public static final Block Wooden_Sieve = new WoodenSieveBlock(
            FabricBlockSettings.of(Material.WOOD).strength(0.2f, 0.2f).sounds(BlockSoundGroup.WOOD));
    public static final Block Evaporation_Pan = new EvaporationPanBlock(
            FabricBlockSettings.of(Material.METAL).strength(3.0f, 3.0f).sounds(BlockSoundGroup.STONE));
    public static final Block Cutting_Board = new CuttingBoardBlock(
            FabricBlockSettings.of(Material.WOOD).strength(0.2f, 0.2f).sounds(BlockSoundGroup.WOOD));
    public static final Block Stone_Mill = new StoneMillBlock(
            FabricBlockSettings.of(Material.STONE).strength(4.0f, 2.0f).sounds(BlockSoundGroup.STONE));
    public static final Block Wooden_Steamer = new WoodenSteamerBlock(
            FabricBlockSettings.of(Material.WOOD).strength(0.3f, 0.3f).sounds(BlockSoundGroup.WOOD));
    public static final Block Smoking_Rack = new SmokingRackBlock(
            FabricBlockSettings.of(Material.WOOD).strength(0.3f, 0.3f).sounds(BlockSoundGroup.WOOD));
    public static final Block Sun_Drying_Tray = new SunDryingTrayBlock(
            FabricBlockSettings.of(Material.WOOD).strength(0.2f, 0.2f).sounds(BlockSoundGroup.WOOD));
    public static final Block Hearth = new HearthBlock(
            FabricBlockSettings.of(Material.STONE).strength(4.0f, 3.0f).sounds(BlockSoundGroup.STONE));
    //草木
    public static final Block Osmanthus_Tree_Sapling = new OsmanthusTreeSaplingBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).noCollision().nonOpaque().breakInstantly());
    public static final Block Osmanthus_Tree_Leaves = new OsmanthusTreeLeavesBlock(
            FabricBlockSettings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block Osmanthus_Tree_Log = createLogBlock(MapColor.GOLD, MapColor.ORANGE);
    public static final Block Orange_Tree_Sapling = new OrangeTreeSaplingBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).noCollision().nonOpaque().breakInstantly());
    public static final Block Orange_Tree_Leaves = new OrangeTreeLeavesBlock(
            FabricBlockSettings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());

    //矿石
    public static final Block Rock_Salt_Ore = new OreBlock(
            FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(5.0f, 3.0f));

    public static void registerBlocks(){
        //装饰方块
        registerBlock("salt_block", Salt_Block);

        //功能方块
        registerBlock("saucepan", Saucepan);
        registerBlock("wooden_sieve", Wooden_Sieve);
        registerBlock("evaporation_pan", Evaporation_Pan);
        registerBlock("cutting_board", Cutting_Board);
        registerBlock("stone_mill", Stone_Mill);
        registerBlock("wooden_steamer", Wooden_Steamer);
        registerBlock("smoking_rack", Smoking_Rack);
        registerBlock("sun_drying_tray", Sun_Drying_Tray);
        registerBlock("hearth", Hearth);

        //草木
        registerBlock("osmanthus_tree_sapling", Osmanthus_Tree_Sapling);
        registerBlock("osmanthus_tree_leaves", Osmanthus_Tree_Leaves);
        registerBlock("osmanthus_tree_log", Osmanthus_Tree_Log);
        registerBlock("orange_tree_sapling", Orange_Tree_Sapling);
        registerBlock("orange_tree_leaves", Orange_Tree_Leaves);

        //矿石
        registerBlock("rock_salt_ore", Rock_Salt_Ore);

        CateCrops.registerCropBlocks();

    }

    private static void registerBlock(String name, Block block) {
        Registry.register(Registry.BLOCK, new Identifier("cate", name), block);
    }
}
