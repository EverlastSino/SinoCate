package com.everlastsino.cate.entity.renderer;

import com.everlastsino.cate.entity.CateEntities;
import com.everlastsino.cate.entity.renderer.renderers.CrabEntityRenderer;
import com.everlastsino.cate.entity.renderer.renderers.OysterEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CateEntityRenderers {

    public static void register() {
        EntityRendererRegistry.register(CateEntities.Oyster_Entity, OysterEntityRenderer::new);
        EntityRendererRegistry.register(CateEntities.Crab_Entity, CrabEntityRenderer::new);
    }

}
