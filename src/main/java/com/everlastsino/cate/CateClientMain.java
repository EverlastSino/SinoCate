package com.everlastsino.cate;

import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.entity.renderer.renderers.OysterEntityRenderer;
import com.everlastsino.cate.screen.CateScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class CateClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //透明方块材质
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Paddy, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Paddy_Seedling, RenderLayer.getCutout());

        //实体渲染
        EntityRendererRegistry.register(CateEntities.Oyster_Entity, OysterEntityRenderer::new);

        //gui注册
        CateScreens.registerScreens();
    }

}
