package com.everlastsino.cate.blockEntity;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.blockEntity.blockEntities.SaucepanBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateBlockEntities {
    public static BlockEntityType<SaucepanBlockEntity> Saucepan_BlockEntity;

    public static void createBlockEntities(){
        Saucepan_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","saucepan_block_entity"),
                FabricBlockEntityTypeBuilder.create(SaucepanBlockEntity::new, CateBlocks.Saucepan).build());
    }
}
