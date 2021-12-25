package com.everlastsino.cate.mixin;

import com.everlastsino.cate.world.CateFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class CateTreeGens {
    @Inject(at = @At("HEAD"), method = "addPlainsFeatures(Lnet/minecraft/world/biome/GenerationSettings$Builder;)V")
    private static void addPlainsFeatures(GenerationSettings.Builder builder, CallbackInfo ci){
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, CateFeatures.Osmanthus_Tree_Placed);
    }
}
