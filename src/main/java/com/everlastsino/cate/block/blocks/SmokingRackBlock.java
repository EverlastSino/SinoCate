package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.blockEntity.blockEntities.SmokingRackBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SmokingRackBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final VoxelShape SHAPE_NS = VoxelShapes.union(
            Block.createCuboidShape(4.0d, 0.0d, 1.0d, 12.0d, 16.0d, 4.0d),
            Block.createCuboidShape(7.0d, 9.0d, 0.0d, 9.0d, 14.0d, 16.0d),
            Block.createCuboidShape(4.0d, 0.0d, 12.0d, 12.0d, 16.0d, 15.0d)
    );
    public static final VoxelShape SHAPE_EW = VoxelShapes.union(
            Block.createCuboidShape(1.0d, 0.0d, 4.0d, 4.0d, 16.0d, 12.0d),
            Block.createCuboidShape(0.0d, 9.0d, 7.0d, 16.0d, 14.0d, 9.0d),
            Block.createCuboidShape(12.0d, 0.0d, 4.0d, 15.0d, 16.0d, 12.0d)
    );

    public SmokingRackBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isOf(Blocks.CAMPFIRE) || blockState.isOf(Blocks.SOUL_CAMPFIRE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SmokingRackBlockEntity entity && hand == Hand.MAIN_HAND) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (player.isSneaking()) {
                ItemStack pickStack = entity.pickItem();
                if (!player.getInventory().insertStack(pickStack)) {
                    player.dropItem(pickStack, false);
                }
            } else if (!itemStack.isEmpty()) {
                if (!entity.insertItem(itemStack)) return ActionResult.PASS;
            } else {
                return ActionResult.PASS;
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, CateBlockEntities.Smoking_Rack_BlockEntity, SmokingRackBlockEntity::tick);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerFacing();
        return this.getDefaultState().with(FACING, (direction == Direction.SOUTH || direction == Direction.NORTH ? direction.getOpposite() : direction).rotateYClockwise());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SmokingRackBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if(blockEntity instanceof SmokingRackBlockEntity entity){
            dropStack(world, pos, entity.stack1);
            dropStack(world, pos, entity.stack2);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST || direction == Direction.EAST) {
            return SHAPE_EW;
        } else {
            return SHAPE_NS;
        }
    }

}