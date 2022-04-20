package com.everlastsino.cate.blockEntity.blockEntities.renderers;

import com.everlastsino.cate.blockEntity.blockEntities.CuttingBoardBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CuttingBoardBlockEntityRenderer implements BlockEntityRenderer<CuttingBoardBlockEntity> {

    public CuttingBoardBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(CuttingBoardBlockEntity entity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        Direction direction = entity.getCachedState().get(CampfireBlock.FACING);
        ItemStack itemStack = entity.itemStack.copy();
        int k = (int)entity.getPos().asLong();
        if (itemStack == ItemStack.EMPTY) return;
        matrixStack.push();
        matrixStack.translate(0.5, 0.1, 0.5);
        Direction direction2 = Direction.fromHorizontal(direction.getHorizontal() % 4);
        float g = -direction2.asRotation();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k);
        matrixStack.pop();
    }

}

