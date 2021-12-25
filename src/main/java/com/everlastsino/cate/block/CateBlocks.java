package com.everlastsino.cate.block;

import com.everlastsino.cate.block.blocks.OsmanthusTreeLeavesBlock;
import com.everlastsino.cate.block.blocks.OsmanthusTreeSaplingBlock;
import com.everlastsino.cate.block.blocks.SaucepanBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateBlocks {

    //功能方块
    public static final Block Saucepan = new SaucepanBlock(
            FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.STONE));

    //草木
    public static final Block Osmanthus_Tree_Sapling = new OsmanthusTreeSaplingBlock(
            FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).noCollision().nonOpaque());
    public static final Block Osmanthus_Tree_Leaves = new OsmanthusTreeLeavesBlock(
            FabricBlockSettings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());

    public static void registerBlocks(){
        //功能方块
        Registry.register(Registry.BLOCK, new Identifier("cate", "saucepan"), Saucepan);

        //草木
        Registry.register(Registry.BLOCK, new Identifier("cate", "osmanthus_tree_sapling"), Osmanthus_Tree_Sapling);
        Registry.register(Registry.BLOCK, new Identifier("cate", "osmanthus_tree_leaves"), Osmanthus_Tree_Leaves);

    }
}
