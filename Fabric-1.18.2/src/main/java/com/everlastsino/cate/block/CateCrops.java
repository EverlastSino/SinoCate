package com.everlastsino.cate.block;

import com.everlastsino.cate.block.blocks.*;
import com.everlastsino.cate.item.CateItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateCrops {

    //耕地型作物
    public static final Block Paddy_Seedling = new CropBlockWithSeed(CateItems.Rough_Rice,
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Adzuki_Bean = new CropBlockWithSeed(CateItems.Adzuki_Bean,
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Ginger = new CropBlockWithSeed(CateItems.Ginger,
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Soybean = new CropBlockWithSeed(CateItems.Soybean,
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Garlic = new CropBlockWithSeed(CateItems.Garlic,
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());
    public static final Block Peanut = new CropBlockWithSeed(CateItems.Peanut,
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());

    //非耕地型作物
    public static final Block Paddy = new PaddyBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).nonOpaque().noCollision());


    public static void registerCropBlocks(){
        //耕地型作物
        registerBlock("paddy_seedling", Paddy_Seedling);
        registerBlock("adzuki_bean", Adzuki_Bean);
        registerBlock("ginger", Ginger);
        registerBlock("soybean", Soybean);
        registerBlock("garlic", Garlic);
        registerBlock("peanut", Peanut);

        //非耕地型作物
        registerBlock("paddy", Paddy);

    }

    private static void registerBlock(String name, Block block) {
        Registry.register(Registry.BLOCK, new Identifier("cate", name), block);
    }

}
