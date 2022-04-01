package com.everlastsino.cate.world.generators;

import com.everlastsino.cate.world.features.CateTreeFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class OsmanthusTreeGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return CateTreeFeatures.Osmanthus_Tree;
    }
}
