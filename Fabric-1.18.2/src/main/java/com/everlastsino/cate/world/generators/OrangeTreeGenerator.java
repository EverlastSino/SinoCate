package com.everlastsino.cate.world.generators;

import com.everlastsino.cate.world.features.CateTreeFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class OrangeTreeGenerator extends SaplingGenerator {
    @Override
    protected RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> getTreeFeature(Random random, boolean bees) {
        return CateTreeFeatures.Orange_Tree;
    }
}
