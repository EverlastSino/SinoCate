package com.everlastsino.cate;

import com.everlastsino.cate.block.CateCrops;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class CateClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Paddy, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Paddy_Seedling, RenderLayer.getCutout());
    }

}
