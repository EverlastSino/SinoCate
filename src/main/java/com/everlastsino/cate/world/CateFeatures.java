package com.everlastsino.cate.world;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.blocks.OsmanthusTreeLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class CateFeatures {

    //树木
    private static TreeFeatureConfig.Builder treeConfigBuilder(Block log, BlockStateProvider provider, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log),
                new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight), provider,
                new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    //configs
    public static final TreeFeatureConfig.Builder Osmanthus_Tree_Config = treeConfigBuilder(CateBlocks.Osmanthus_tree_Log,
            new WeightedBlockStateProvider(new DataPool.Builder<BlockState>().add(CateBlocks.Osmanthus_Tree_Leaves.getDefaultState(), 1)
                    .add(CateBlocks.Osmanthus_Tree_Leaves.getDefaultState().with(OsmanthusTreeLeavesBlock.GROWABLE, true), 1)),
            5, 2, 0, 2).ignoreVines();

    //configure
    public static final ConfiguredFeature<TreeFeatureConfig, ?> Osmanthus_Tree = Feature.TREE.configure(Osmanthus_Tree_Config.build());

    //placed_features
    public static final PlacedFeature Osmanthus_Tree_Placed = Osmanthus_Tree.withPlacement(
            VegetationPlacedFeatures.modifiersWithWouldSurvive(
                    PlacedFeatures.createCountExtraModifier(0, 0.01f, 1),
                    CateBlocks.Osmanthus_Tree_Sapling));

    public static void registerFeatures(){
        //树木
        //configured
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("cate","osmanthus_tree"), Osmanthus_Tree);

        //placed
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("cate", "osmanthus_tree_placed"), Osmanthus_Tree_Placed);

    }
}
