package com.everlastsino.cate.mixin;

import com.everlastsino.cate.entity.CateEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(OverworldBiomeCreator.class)
public class CateEntityGens {

    @ModifyVariable(method = "createBeach(ZZ)Lnet/minecraft/world/biome/Biome;", at = @At("STORE"), ordinal = 0)
    private static SpawnSettings.Builder injectFunction1(SpawnSettings.Builder builder) {
        return builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(CateEntities.Oyster_Entity,6,2,4));
    }
    @ModifyVariable(method = "createWarmOcean()Lnet/minecraft/world/biome/Biome;", at = @At("STORE"), ordinal = 0)
    private static SpawnSettings.Builder injectFunction2(SpawnSettings.Builder builder) {
        return builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(CateEntities.Oyster_Entity,6,2,6));
    }
    @ModifyVariable(method = "createLukewarmOcean(Z)Lnet/minecraft/world/biome/Biome;", at = @At("STORE"), ordinal = 0)
    private static SpawnSettings.Builder injectFunction3(SpawnSettings.Builder builder) {
        return builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(CateEntities.Oyster_Entity,6,2,6));
    }

}