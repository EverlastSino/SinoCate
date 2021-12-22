package com.everlastsino.cate.block;

import com.everlastsino.cate.block.blocks.SaucepanBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateBlocks {
    public static final Block Saucepan = new SaucepanBlock(
            FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.STONE));

    public static void registerBlocks(){
        Registry.register(Registry.BLOCK, new Identifier("cate", "saucepan"), Saucepan);
    }
}
