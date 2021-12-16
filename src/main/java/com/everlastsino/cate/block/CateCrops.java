package com.everlastsino.cate.block;

import com.everlastsino.cate.block.blocks.PaddyBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateCrops {

    //耕地型作物


    //非耕地型作物
    public static final Block Paddy = new PaddyBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision());


    public static void registerCropBlocks(){
        Registry.register(Registry.BLOCK, new Identifier("cate", "paddy"), Paddy);
    }


}
