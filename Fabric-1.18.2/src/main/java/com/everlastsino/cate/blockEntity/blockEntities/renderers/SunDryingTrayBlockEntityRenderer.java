package com.everlastsino.cate.blockEntity.blockEntities.renderers;

import com.everlastsino.cate.blockEntity.blockEntities.SunDryingTrayBlockEntity;
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
public class SunDryingTrayBlockEntityRenderer implements BlockEntityRenderer<SunDryingTrayBlockEntity> {

    public SunDryingTrayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    public void render(SunDryingTrayBlockEntity entity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        DefaultedList<ItemStack> defaultedList = entity.itemStacks;
        int k = (int)entity.getPos().asLong();

        for(int l = 0; l < 4; ++l) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack != ItemStack.EMPTY) {
                matrixStack.push();
                matrixStack.translate(0.5D, 0.1d, 0.5D);
                Direction direction2 = Direction.fromHorizontal(l % 4);
                float g = -direction2.asRotation();
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                matrixStack.translate(-0.1D, -0.2D, 0.0D);
                matrixStack.scale(0.35F, 0.35F, 0.35F);
                MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, i, j, matrixStack, vertexConsumerProvider, k + l);
                matrixStack.pop();
            }
        }
    }
}
