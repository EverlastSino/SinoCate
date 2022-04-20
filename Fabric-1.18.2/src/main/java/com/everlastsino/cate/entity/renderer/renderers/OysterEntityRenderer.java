package com.everlastsino.cate.entity.renderer.renderers;

import com.everlastsino.cate.entity.entities.OysterEntity;
import com.everlastsino.cate.entity.models.OysterEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class OysterEntityRenderer extends MobEntityRenderer<OysterEntity, OysterEntityModel> {

    public OysterEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new OysterEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(OysterEntity entity) {
        return new Identifier("cate", "textures/entity/oyster/oyster.png");
    }

}