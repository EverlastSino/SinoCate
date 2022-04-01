package com.everlastsino.cate;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.blockEntity.blockEntities.renderers.CuttingBoardBlockEntityRenderer;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.entity.renderer.renderers.OysterEntityRenderer;
import com.everlastsino.cate.screen.CateScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class CateClientMain implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("SinoCateClient");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Start to initialize client settings.");

        //透明方块材质
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Paddy, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Paddy_Seedling, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Adzuki_Bean, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateCrops.Ginger, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(CateBlocks.Osmanthus_Tree_Sapling, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateBlocks.Osmanthus_Tree_Leaves, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CateBlocks.Wooden_Sieve, RenderLayer.getCutout());

        //实体渲染
        EntityRendererRegistry.register(CateEntities.Oyster_Entity, OysterEntityRenderer::new);

        //gui注册
        CateScreens.registerScreens();

        //方块实体
        BlockEntityRendererRegistry.register(CateBlockEntities.Cutting_Board_BlockEntity, CuttingBoardBlockEntityRenderer::new);

        LOGGER.info("Successfully initialized!");
    }

}
