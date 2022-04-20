package com.everlastsino.cate.mixin;

import com.everlastsino.cate.world.features.CateOreFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class CateOreGens {
    @Inject(at = @At("HEAD"), method = "addDefaultOres(Lnet/minecraft/world/biome/GenerationSettings$Builder;)V")
    private static void addDefaultOres(GenerationSettings.Builder builder, CallbackInfo ci){
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, CateOreFeatures.ORE_ROCK_SALT_UPPER);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, CateOreFeatures.ORE_ROCK_SALT_MIDDLE);
    }
}
