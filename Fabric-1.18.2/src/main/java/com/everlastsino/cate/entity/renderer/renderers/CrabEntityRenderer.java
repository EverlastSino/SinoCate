package com.everlastsino.cate.entity.renderer.renderers;

import com.everlastsino.cate.entity.entities.CrabEntity;
import com.everlastsino.cate.entity.models.CrabEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrabEntityRenderer extends GeoEntityRenderer<CrabEntity> {
    public CrabEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CrabEntityModel());
    }

}
