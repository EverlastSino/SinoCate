package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.item.CateItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WoodenSieveBlock extends Block {

    public static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 13.0D, 1.0D),
            Block.createCuboidShape(0.0D, 0.0D, 15.0D, 1.0D, 13.0D, 16.0D),
            Block.createCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 13.0D, 1.0D),
            Block.createCuboidShape(15.0D, 0.0D, 15.0D, 16.0D, 13.0D, 16.0D),
            VoxelShapes.combineAndSimplify(
                    Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                    Block.createCuboidShape(1.0D, 14.0D, 1.0D, 15.0D, 16.0D, 15.0D),
                    BooleanBiFunction.ONLY_FIRST
            )
    );

    public WoodenSieveBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && !world.isClient) {
            ItemStack stack = itemEntity.getStack();
            double y = entity.getY();
            if (y >= (double) pos.getY() + 0.8){
                ItemStack dropItem = ItemStack.EMPTY;
                if (stack.isOf(CateItems.Paddy_Straw)) {
                    dropItem = new ItemStack(CateItems.Rough_Rice);
                }else if (stack.isOf(CateItems.Rough_Rice)){
                    dropItem = new ItemStack(CateItems.Rice);
                }
                if (dropItem.isEmpty()) return;
                stack.decrement(1);
                world.spawnEntity(new ItemEntity(world, entity.getX(), y - 0.5, entity.getZ(), dropItem));
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
