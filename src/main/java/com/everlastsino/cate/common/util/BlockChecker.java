package com.everlastsino.cate.common.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record BlockChecker(Block block, TagKey<Block> tag, Type type) {
    enum Type {
        TAG,
        BLOCK;
    }

    public static BlockChecker of(Block block) {
        return new BlockChecker(block, null, Type.BLOCK);
    }

    public static BlockChecker of(TagKey<Block> tag) {
        return new BlockChecker(null, tag, Type.TAG);
    }

    public boolean check(BlockState state) {
        return this.type == Type.TAG ? state.is(this.tag) : state.is(this.block);
    }
}
