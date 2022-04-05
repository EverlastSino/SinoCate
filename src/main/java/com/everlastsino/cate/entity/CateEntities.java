package com.everlastsino.cate.entity;

import com.everlastsino.cate.entity.entities.OysterEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateEntities{

    public static EntityType<OysterEntity> Oyster_Entity = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("cate", "oyster"),
            FabricEntityTypeBuilder.createLiving().entityFactory(OysterEntity::new).spawnGroup(SpawnGroup.WATER_AMBIENT)
                    .defaultAttributes(() -> OysterEntity.createMobAttributes()
                            .add(EntityAttributes.GENERIC_MAX_HEALTH, 12))
                    .dimensions(EntityDimensions.fixed(0.5f, 0.25f)).build()
    );

}