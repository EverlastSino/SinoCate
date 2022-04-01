package com.everlastsino.cate.world.features;

import com.everlastsino.cate.block.CateBlocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class CateOreFeatures {

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModfier) {
        return modifiers(CountPlacementModifier.of(count), heightModfier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }

    //target
    public static final List<OreFeatureConfig.Target> ROCK_SALT_ORES = List.of(OreFeatureConfig.createTarget(
            OreConfiguredFeatures.STONE_ORE_REPLACEABLES, CateBlocks.Rock_Salt_Ore.getDefaultState()));

    //configured
    public static final ConfiguredFeature<?, ?> ORE_ROCK_SALT = Feature.ORE.configure(new OreFeatureConfig(ROCK_SALT_ORES, 9));

    //placed
    public static final PlacedFeature ORE_ROCK_SALT_UPPER = ORE_ROCK_SALT.withPlacement(
            modifiersWithCount(60, HeightRangePlacementModifier.trapezoid(YOffset.fixed(57), YOffset.fixed(128))));
    public static final PlacedFeature ORE_ROCK_SALT_MIDDLE = ORE_ROCK_SALT.withPlacement(
            modifiersWithCount(10, HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(56))));


    public static void registerOreFeatures() {
        //configured
        ConfiguredFeatures.register("ore_rock_salt", ORE_ROCK_SALT);

        //placed
        PlacedFeatures.register("ore_rock_salt_upper", ORE_ROCK_SALT_UPPER);
        PlacedFeatures.register("ore_rock_salt_middle", ORE_ROCK_SALT_MIDDLE);

    }


}
