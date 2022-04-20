package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.blockEntity.blockEntities.CuttingBoardBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    protected static final VoxelShape SHAPE_NS = Block.createCuboidShape(1.0D, 0.0D, 3.0D, 15.0D, 1.5D, 13.0D);
    protected static final VoxelShape SHAPE_WE = Block.createCuboidShape(3.0D, 0.0D, 1.0D, 13.0D, 1.5D, 15.0D);

    public CuttingBoardBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CuttingBoardBlockEntity entity && hand == Hand.MAIN_HAND) {
            ItemStack stackInHand = player.getStackInHand(hand);
            if (entity.hasItem()) {
                if (entity.operate(stackInHand) && entity.matchedCorrect) {
                    if (entity.damageTool > 0) {
                        if (stackInHand.isDamageable()) {
                            stackInHand.damage(1, player, p -> p.sendToolBreakStatus(hand));
                        } else {
                            ItemStack giveStack = new ItemStack(stackInHand.getItem().getRecipeRemainder());
                            if (giveStack.isEmpty() && stackInHand.isOf(Items.POTION)) giveStack = new ItemStack(Items.GLASS_BOTTLE);
                            stackInHand.decrement(1);
                            if (!player.getInventory().insertStack(giveStack)) {
                                player.dropItem(giveStack, false);
                            }
                        }
                    }
                    return ActionResult.SUCCESS;
                } else {
                    if (stackInHand.isEmpty() && player.isSneaking()) {
                        dropStack(world, pos, entity.popStack());
                        return ActionResult.SUCCESS;
                    }
                    return entity.insertStack(stackInHand, player.isCreative()) ? ActionResult.SUCCESS : ActionResult.PASS;
                }
            } else if (stackInHand.isEmpty() && player.isSneaking()) {
                dropStack(world, pos, entity.popStack());
                return ActionResult.SUCCESS;
            }
            return entity.insertStack(stackInHand, player.isCreative()) ? ActionResult.SUCCESS : ActionResult.PASS;
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CuttingBoardBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if(blockEntity instanceof CuttingBoardBlockEntity entity){
            dropStack(world, pos, entity.popStack());
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return (direction == Direction.NORTH || direction == Direction.SOUTH) ? SHAPE_NS : SHAPE_WE;
    }

}