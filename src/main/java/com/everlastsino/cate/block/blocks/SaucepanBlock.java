package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.blockEntity.blockEntities.SaucepanBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SaucepanBlock extends BlockWithEntity{

    public SaucepanBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SaucepanBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof SaucepanBlockEntity entity){
            DefaultedList<ItemStack> itemStacks = entity.getItems();
            for(ItemStack stack : itemStacks){
                dropStack((World) world, pos, stack);
            }
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return SaucepanBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SaucepanBlockEntity(pos, state);
    }


}

