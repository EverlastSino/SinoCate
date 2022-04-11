package com.everlastsino.cate.world.features;

import com.everlastsino.cate.block.CateBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

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
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_ROCK_SALT =
            ConfiguredFeatures.register("ore_rock_salt", Feature.ORE, new OreFeatureConfig(ROCK_SALT_ORES, 9));

    //placed
    public static final RegistryEntry<PlacedFeature> ORE_ROCK_SALT_UPPER = PlacedFeatures.register(
            "ore_rock_salt_upper", ORE_ROCK_SALT,
            modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(50), YOffset.fixed(180))));
    public static final RegistryEntry<PlacedFeature> ORE_ROCK_SALT_MIDDLE = PlacedFeatures.register(
            "ore_rock_salt_middle", ORE_ROCK_SALT,
            modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(49))));

    public static void register(){}

}
