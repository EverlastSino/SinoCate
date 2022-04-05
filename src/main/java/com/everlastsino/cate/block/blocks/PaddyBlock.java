package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.block.CateCrops;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PaddyBlock extends TallPlantBlock implements Waterloggable, Fertilizable {
    public static final EnumProperty<DoubleBlockHalf> HALF = TallPlantBlock.HALF; //双层方块
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED; //含水方块
    public static final IntProperty AGE = Properties.AGE_3;
    protected static final VoxelShape[] SHAPE = {
            Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0),
    }; // age 0~1;

    public PaddyBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3 && state.get(HALF) == DoubleBlockHalf.LOWER; //age小于3且是下方方块
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER)); //如果原来有水，就设置fluidState为水
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {} //覆写空值，取消原本的双格检查

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED) && random.nextInt(10) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9){
            this.grow(world, random, pos, state); //生长随机刻，下方方块必须在水中
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world)); //扩散流水
        }
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.UPPER && !world.getBlockState(pos.down()).isOf(CateCrops.Paddy)) {
            return Blocks.AIR.getDefaultState(); //若下方方块被破坏，则上方一起破坏
        }
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER && !world.getBlockState(pos.up()).isOf(CateCrops.Paddy)) {
            return Blocks.AIR.getDefaultState(); //若上方方块被破坏，则下方一起破坏
        }
        return state;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE[state.get(AGE) / 2];
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return ((floor.isOf(Blocks.CLAY) || floor.isOf(Blocks.FARMLAND)) &&
                world.getFluidState(pos.up()).isOf(Fluids.WATER) && world.getFluidState(pos.up(2)).isOf(Fluids.EMPTY))
                || floor.isOf(CateCrops.Paddy); //可以种植在粘土和耕地上
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(CateCrops.Paddy);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);
        super.appendProperties(builder);
    }


    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 3; //骨粉催熟
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = state.get(AGE);
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            if (i > 0 && (world.getBlockState(pos.up()).isOf(Blocks.AIR) || world.getBlockState(pos.up()).isOf(CateCrops.Paddy))
                    && world.getFluidState(pos.up()).isOf(Fluids.EMPTY)) { //若age >= 1，则可以生长上方部分方块
                world.setBlockState(pos.up(),
                        state.with(AGE, i + 1).with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, false));
            }
        } else if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(AGE, i + 1), Block.NOTIFY_LISTENERS);
        }
    }
}

