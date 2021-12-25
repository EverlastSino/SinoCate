package com.everlastsino.cate.block;

import com.everlastsino.cate.block.blocks.AdzukiBeanBlock;
import com.everlastsino.cate.block.blocks.GingerBlock;
import com.everlastsino.cate.block.blocks.PaddyBlock;
import com.everlastsino.cate.block.blocks.PaddySeedlingBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateCrops {

    //耕地型作物
    public static final Block Paddy_Seedling = new PaddySeedlingBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Adzuki_Bean = new AdzukiBeanBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Ginger = new GingerBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());

    //非耕地型作物
    public static final Block Paddy = new PaddyBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());


    public static void registerCropBlocks(){
        //耕地型作物
        Registry.register(Registry.BLOCK, new Identifier("cate", "paddy_seedling"), Paddy_Seedling);
        Registry.register(Registry.BLOCK, new Identifier("cate", "adzuki_bean"), Adzuki_Bean);
        Registry.register(Registry.BLOCK, new Identifier("cate", "ginger"), Ginger);

        //非耕地型作物
        Registry.register(Registry.BLOCK, new Identifier("cate", "paddy"), Paddy);

    }


}
