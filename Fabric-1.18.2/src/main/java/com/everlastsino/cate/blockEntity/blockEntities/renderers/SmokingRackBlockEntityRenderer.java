package com.everlastsino.cate.blockEntity.blockEntities.renderers;

import com.everlastsino.cate.blockEntity.blockEntities.SmokingRackBlockEntity;
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
public class SmokingRackBlockEntityRenderer implements BlockEntityRenderer<SmokingRackBlockEntity> {

    public SmokingRackBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(SmokingRackBlockEntity entity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        ItemStack stack1 = entity.stack1.copy(), stack2 = entity.stack2.copy();
        int k = (int)entity.getPos().asLong();

        Direction direction = entity.getCachedState().get(CampfireBlock.FACING);

        matrixStack.push();
        matrixStack.scale(0.4f, 0.4f, 0.4f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(direction.rotateYClockwise().asRotation()));

        double x = direction == Direction.SOUTH || direction == Direction.WEST ? -0.86d : 1.64d;
        double z = direction == Direction.NORTH || direction == Direction.WEST ? -1.25d : 1.25d;

        matrixStack.translate(x, 1.1d, z);
        if (!stack1.isEmpty()) {
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k);
        }

        matrixStack.translate(-0.78f, 0f, 0f);
        if (!stack2.isEmpty()) {
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k);
        }

        matrixStack.pop();
    }

}

