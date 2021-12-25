package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.item.CateItems;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class AdzukiBeanBlock extends CropBlock {

    public AdzukiBeanBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return CateItems.Adzuki_Bean;
    }

}
