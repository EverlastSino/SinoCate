package com.everlastsino.cate.entity.models;

import com.everlastsino.cate.entity.entities.OysterEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OysterEntityModel extends EntityModel<OysterEntity> {

    private final ModelPart base;

    public OysterEntityModel() {
        List<ModelPart.Cuboid> list = new ArrayList<>();
        Map<String, ModelPart> map = new HashMap<>();
        list.add(new ModelPart.Cuboid(0,0,-4, 2, -4, 8, 4, 8,0.0F, 0.0F, 0.0F,false,64,32));
        base = new ModelPart(list, map);
    }

    @Override
    public void setAngles(OysterEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // translate model down
        matrices.translate(0, 1.125, 0);

        // render Oyster
        base.render(matrices, vertices, light, overlay);
    }
}