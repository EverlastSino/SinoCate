package com.everlastsino.cate.world.features;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.blocks.OsmanthusTreeLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
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

    //configs
    public static final TreeFeatureConfig.Builder Osmanthus_Tree_Config = treeConfigBuilder(CateBlocks.Osmanthus_Tree_Log,
            new WeightedBlockStateProvider(new DataPool.Builder<BlockState>().add(CateBlocks.Osmanthus_Tree_Leaves.getDefaultState(), 1)
                    .add(CateBlocks.Osmanthus_Tree_Leaves.getDefaultState().with(OsmanthusTreeLeavesBlock.GROWABLE, true), 1)),
            5, 2, 0, 2).ignoreVines();

    //configure
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> Osmanthus_Tree =
            ConfiguredFeatures.register("osmanthus_tree", Feature.TREE, Osmanthus_Tree_Config.build());

    //placed_features
    public static final RegistryEntry<PlacedFeature> Osmanthus_Tree_Placed = PlacedFeatures.register(
            "osmanthus_tree_placed", Osmanthus_Tree,
            PlacedFeatures.wouldSurvive(CateBlocks.Osmanthus_Tree_Log));

    public static void registerTreeFeatures(){

    }
}
