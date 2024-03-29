package com.everlastsino.cate.blockEntity;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.blockEntity.blockEntities.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateBlockEntities {
    public static BlockEntityType<SaucepanBlockEntity> Saucepan_BlockEntity;
    public static BlockEntityType<CuttingBoardBlockEntity> Cutting_Board_BlockEntity;
    public static BlockEntityType<StoneMillBlockEntity> Stone_Mill_BlockEntity;
    public static BlockEntityType<WoodenSteamerBlockEntity> Wooden_Steamer_BlockEntity;
    public static BlockEntityType<SmokingRackBlockEntity> Smoking_Rack_BlockEntity;
    public static BlockEntityType<SunDryingTrayBlockEntity> Sun_Drying_Tray_BlockEntity;

    public static void createBlockEntities(){
        Saucepan_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","saucepan_block_entity"),
                FabricBlockEntityTypeBuilder.create(SaucepanBlockEntity::new, CateBlocks.Saucepan).build());
        Cutting_Board_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","cutting_board_block_entity"),
                FabricBlockEntityTypeBuilder.create(CuttingBoardBlockEntity::new, CateBlocks.Cutting_Board).build());
        Stone_Mill_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","stone_mill_block_entity"),
                FabricBlockEntityTypeBuilder.create(StoneMillBlockEntity::new, CateBlocks.Stone_Mill).build());
        Wooden_Steamer_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","wooden_steamer_block_entity"),
                FabricBlockEntityTypeBuilder.create(WoodenSteamerBlockEntity::new, CateBlocks.Wooden_Steamer).build());
        Smoking_Rack_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","smoking_rack_block_entity"),
                FabricBlockEntityTypeBuilder.create(SmokingRackBlockEntity::new, CateBlocks.Smoking_Rack).build());
        Sun_Drying_Tray_BlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("cate","sun_drying_tray_block_entity"),
                FabricBlockEntityTypeBuilder.create(SunDryingTrayBlockEntity::new, CateBlocks.Sun_Drying_Tray).build());
    }
}
