package com.everlastsino.cate;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.blockEntity.blockEntities.renderers.CuttingBoardBlockEntityRenderer;
import com.everlastsino.cate.blockEntity.blockEntities.renderers.SmokingRackBlockEntityRenderer;
import com.everlastsino.cate.blockEntity.blockEntities.renderers.SunDryingTrayBlockEntityRenderer;
import com.everlastsino.cate.blockEntity.blockEntities.renderers.WoodenSteamerBlockEntityRenderer;
import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.entity.renderer.renderers.OysterEntityRenderer;
import com.everlastsino.cate.screen.CateScreens;
import com.everlastsino.cate.util.cookingExp.AllCookingExpStates;
import com.everlastsino.cate.util.cookingExp.CookingExperienceState;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class CateClientMain implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("SinoCateClient");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Start to initialize client settings.");

        //透明方块材质
        transparent(CateCrops.Paddy);
        transparent(CateCrops.Paddy_Seedling);
        transparent(CateCrops.Adzuki_Bean);
        transparent(CateCrops.Ginger);
        transparent(CateCrops.Soybean);

        transparent(CateBlocks.Osmanthus_Tree_Sapling);
        transparent(CateBlocks.Osmanthus_Tree_Leaves);
        transparent(CateBlocks.Orange_Tree_Sapling);
        transparent(CateBlocks.Orange_Tree_Leaves);
        transparent(CateBlocks.Wooden_Sieve);

        //实体渲染
        EntityRendererRegistry.register(CateEntities.Oyster_Entity, OysterEntityRenderer::new);

        //gui注册
        CateScreens.registerScreens();

        //方块实体
        BlockEntityRendererRegistry.register(CateBlockEntities.Cutting_Board_BlockEntity, CuttingBoardBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CateBlockEntities.Wooden_Steamer_BlockEntity, WoodenSteamerBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CateBlockEntities.Smoking_Rack_BlockEntity, SmokingRackBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CateBlockEntities.Sun_Drying_Tray_BlockEntity, SunDryingTrayBlockEntityRenderer::new);

        HudRenderCallback.EVENT.register(CateClientMain::renderCookingExpBar);

        LOGGER.info("Successfully initialized!");
    }

    public static void transparent(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static void renderCookingExpBar(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        InGameHud inGameHud = client.inGameHud;
        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();
        if (client.player.getAbilities().creativeMode) return;
        int x = scaledWidth / 2 + 95;
        int l;
        int k;
        client.getProfiler().push("cookingExpBar");
        RenderSystem.setShaderTexture(0, new Identifier("cate", "textures/gui/icons.png"));
        CookingExperienceState state = AllCookingExpStates.INSTANCE.get(client.player);
        int i = CookingExperienceState.getMaxExpByLevel(state.level);
        if (i > 0) {
            k = (int)(state.experience / CookingExperienceState.getMaxExpByLevel(state.level) * 91.5f);
            l = scaledHeight - 10 + 3;
            inGameHud.drawTexture(matrices, x, l, 0, 0, 91, 5);
            if (k > 0) {
                inGameHud.drawTexture(matrices, x, l, 0, 5, k, 5);
            }
        }
        client.getProfiler().pop();
        if (state.level > 0) {
            client.getProfiler().push("cookingExpLevel");
            String string = "" + state.level;
            k = (scaledWidth - inGameHud.getTextRenderer().getWidth(string)) / 2 + 142;
            l = scaledHeight - 9 - 4;
            inGameHud.getTextRenderer().draw(matrices, string, (float)(k + 1), (float)l, 0);
            inGameHud.getTextRenderer().draw(matrices, string, (float)(k - 1), (float)l, 0);
            inGameHud.getTextRenderer().draw(matrices, string, (float)k, (float)(l + 1), 0);
            inGameHud.getTextRenderer().draw(matrices, string, (float)k, (float)(l - 1), 0);
            inGameHud.getTextRenderer().draw(matrices, string, (float)k, (float)l, 0xff9900);
            client.getProfiler().pop();
        }
    }

}
