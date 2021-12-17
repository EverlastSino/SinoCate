package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.item.CateItems;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class PaddySeedlingBlock extends CropBlock {

    public PaddySeedlingBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return CateItems.Rough_Rice;
    }

}