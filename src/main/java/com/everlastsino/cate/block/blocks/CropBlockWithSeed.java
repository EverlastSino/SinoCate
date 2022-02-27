package com.everlastsino.cate.block.blocks;

import net.minecraft.block.CropBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

public class CropBlockWithSeed extends CropBlock {
    private final Item seed;

    public CropBlockWithSeed(Item seed, Settings settings) {
        super(settings);
        this.seed = seed;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return this.seed;
    }

}
