package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.world.generators.OsmanthusTreeGenerator;
import net.minecraft.block.SaplingBlock;

public class OsmanthusTreeSaplingBlock extends SaplingBlock {
    public OsmanthusTreeSaplingBlock(Settings settings) {
        super(new OsmanthusTreeGenerator(), settings);
    }
}
