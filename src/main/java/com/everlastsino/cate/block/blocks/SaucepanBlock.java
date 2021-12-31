package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.blockEntity.blockEntities.SaucepanBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SaucepanBlock extends BlockWithEntity{

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D),
            Block.createCuboidShape(0.0D, 9.0D, 6.0D, 1.0D, 10.0D, 10.0D),
            Block.createCuboidShape(15.0D, 9.0D, 6.0D, 16.0D, 10.0D, 10.0D),
            Block.createCuboidShape(6.0D, 11.0D, 7.0D, 7.0D, 12.0D, 9.0D),
            Block.createCuboidShape(9.0D, 11.0D, 7.0D, 10.0D, 12.0D, 9.0D),
            Block.createCuboidShape(6.0D, 12.0D, 7.0D, 10.0D, 13.0D, 9.0D)
    );
    public static final VoxelShape SHAPE_TURNED = VoxelShapes.union(
            Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D),
            Block.createCuboidShape(6.0D, 9.0D, 0.0D, 10.0D, 10.0D, 1.0D),
            Block.createCuboidShape(6.0D, 9.0D, 15.0D, 10.0D, 10.0D, 16.0D),
            Block.createCuboidShape(7.0D, 11.0D, 6.0D, 9.0D, 12.0D, 7.0D),
            Block.createCuboidShape(7.0D, 11.0D, 9.0D, 9.0D, 12.0D, 10.0D),
            Block.createCuboidShape(7.0D, 12.0D, 6.0D, 9.0D, 13.0D, 10.0D)
    );

    public SaucepanBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
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
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if(blockEntity instanceof SaucepanBlockEntity entity){
            DefaultedList<ItemStack> itemStacks = entity.getItems();
            for(ItemStack itemStack : itemStacks){
                dropStack(world, pos, itemStack);
            }
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, CateBlockEntities.Saucepan_BlockEntity, SaucepanBlockEntity::tick);
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SaucepanBlockEntity(pos, state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST || direction == Direction.EAST) {
            return SHAPE_TURNED;
        }else{
            return SHAPE;
        }
    }


}

