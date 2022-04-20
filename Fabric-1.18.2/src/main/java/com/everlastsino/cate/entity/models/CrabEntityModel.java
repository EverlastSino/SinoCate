package com.everlastsino.cate.entity.models;

import com.everlastsino.cate.entity.entities.CrabEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrabEntityModel extends AnimatedGeoModel<CrabEntity> {
    @Override
    public Identifier getModelLocation(CrabEntity object) {
        return new Identifier("cate", "geo/entity/crab.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CrabEntity object) {
        return new Identifier("cate", "textures/entity/crab/crab.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CrabEntity animatable) {
        return new Identifier("cate", "animations/entity/crab.animation.json");
    }
}
