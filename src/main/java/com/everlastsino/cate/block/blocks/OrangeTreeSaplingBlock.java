package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.world.generators.OrangeTreeGenerator;
import net.minecraft.block.SaplingBlock;

public class OrangeTreeSaplingBlock extends SaplingBlock {
    public OrangeTreeSaplingBlock(Settings settings) {
        super(new OrangeTreeGenerator(), settings);
    }
}
