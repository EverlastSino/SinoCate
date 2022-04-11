package com.everlastsino.cate.world.features;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.blocks.OsmanthusTreeLeavesBlock;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.impl.content.registry.CompostingChanceRegistryImpl;
import net.fabricmc.fabric.impl.transfer.item.ComposterWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class CateTreeFeatures {
    //树木
    private static TreeFeatureConfig.Builder treeConfigBuilder(Block log, BlockStateProvider provider, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log),
                new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight), provider,
                new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeFeatureConfig.Builder fruitTreeBuilder(Block log, Block leaves) {
        return treeConfigBuilder(log,
                new WeightedBlockStateProvider(new DataPool.Builder<BlockState>().add(leaves.getDefaultState(), 2)
                        .add(leaves.getDefaultState().with(OsmanthusTreeLeavesBlock.GROWABLE, true), 1)),
                5, 2, 0, 2).ignoreVines();
    }

    //configs
    public static final TreeFeatureConfig.Builder Osmanthus_Tree_Config = fruitTreeBuilder(
            CateBlocks.Osmanthus_Tree_Log, CateBlocks.Osmanthus_Tree_Leaves);
    public static final TreeFeatureConfig.Builder Orange_Tree_Config = fruitTreeBuilder(
            Blocks.OAK_LOG, CateBlocks.Orange_Tree_Leaves);

    //configure
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> Osmanthus_Tree =
            ConfiguredFeatures.register("osmanthus_tree", Feature.TREE, Osmanthus_Tree_Config.build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> Orange_Tree =
            ConfiguredFeatures.register("orange_tree", Feature.TREE, Orange_Tree_Config.build());

    //placed_features
    public static final RegistryEntry<PlacedFeature> Osmanthus_Tree_Placed = PlacedFeatures.register("osmanthus_tree_placed",
            Osmanthus_Tree,
            PlacedFeatures.createCountExtraModifier(0, 0.01F, 1),
            SquarePlacementModifier.of(), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
            BlockFilterPlacementModifier.of(
                    BlockPredicate.wouldSurvive(CateBlocks.Osmanthus_Tree_Sapling.getDefaultState(), BlockPos.ORIGIN))
            , BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> Orange_Tree_Placed = PlacedFeatures.register("orange_tree_placed",
            Orange_Tree,
            PlacedFeatures.createCountExtraModifier(0, 0.01F, 1),
            SquarePlacementModifier.of(), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
            BlockFilterPlacementModifier.of(
                    BlockPredicate.wouldSurvive(CateBlocks.Orange_Tree_Sapling.getDefaultState(), BlockPos.ORIGIN))
            , BiomePlacementModifier.of());

    public static void register(){}

}
