package com.everlastsino.cate.blockEntity.blockEntities.renderers;

import com.everlastsino.cate.blockEntity.blockEntities.WoodenSteamerBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class WoodenSteamerBlockEntityRenderer implements BlockEntityRenderer<WoodenSteamerBlockEntity> {

    public WoodenSteamerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    public void render(WoodenSteamerBlockEntity woodenSteamerEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        DefaultedList<ItemStack> defaultedList = woodenSteamerEntity.downItems;
        int k = (int)woodenSteamerEntity.getPos().asLong();

        for(int l = 0; l < 4; ++l) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack != ItemStack.EMPTY) {
                matrixStack.push();
                matrixStack.translate(0.5D, 0.25D, 0.5D);
                Direction direction2 = Direction.fromHorizontal(l % 4);
                float g = -direction2.asRotation();
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                matrixStack.translate(-0.1D, -0.2D, 0.0D);
                matrixStack.scale(0.375F, 0.375F, 0.375F);
                MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k + l);
                matrixStack.pop();
            }
        }

        DefaultedList<ItemStack> defaultedListUp = woodenSteamerEntity.upItems;
        k = (int)woodenSteamerEntity.getPos().asLong();

        for(int l = 0; l < 4; ++l) {
            ItemStack itemStack = defaultedListUp.get(l);
            if (itemStack != ItemStack.EMPTY) {
                matrixStack.push();
                matrixStack.translate(0.5D, 0.75D, 0.5D);
                Direction direction2 = Direction.fromHorizontal(l % 4);
                float g = -direction2.asRotation();
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                matrixStack.translate(-0.2D, -0.2D, 0.0D);
                matrixStack.scale(0.375F, 0.375F, 0.375F);
                MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k + l);
                matrixStack.pop();
            }
        }

    }
}
