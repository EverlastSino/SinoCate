package com.everlastsino.cate.common.block.base;

import com.everlastsino.cate.common.util.BlockChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SubordinatedPlantBlock extends DirectionalBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    protected static final VoxelShape[][] SHAPES = new VoxelShape[][]{ //age, direction
            createShape(3, 4), createShape(4, 3), createShape(5, 3)
    };

    private static VoxelShape[] createShape(int height, int xzOffset) {
        return new VoxelShape[]{
                Block.box(xzOffset, 0.0, xzOffset, 16 - xzOffset, height, 16 - xzOffset),
                Block.box(xzOffset, 16 - height, xzOffset, 16 - xzOffset, 16.0, 16 - xzOffset),
                Block.box(xzOffset, xzOffset, 0.0, 16 - xzOffset, 16 - xzOffset, height),
                Block.box(xzOffset, xzOffset, 16 - height, 16 - xzOffset, 16 - xzOffset, 16.0),
                Block.box(0.0, xzOffset, xzOffset, height + 0.001d, 16 - xzOffset, 16 - xzOffset),
                Block.box(16 - height, xzOffset, xzOffset, 16.0, 16 - xzOffset, 16 - xzOffset)
        };
    }

    private final BlockChecker checker;

    public SubordinatedPlantBlock(BlockChecker checker, Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP).setValue(AGE, 0));
        this.checker = checker;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        final var age = state.getValue(AGE);
        if (age < 2 && world.random.nextInt(5) == 0) performBonemeal(world, random, pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean b) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), Block.UPDATE_ALL);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return checker.check(world.getBlockState(pos.relative(state.getValue(FACING))));
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPES[state.getValue(AGE)][state.getValue(FACING).get3DDataValue()];
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (!state.canSurvive(level, pos)) ((Level) level).setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        super.onNeighborChange(state, level, pos, neighbor);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getClickedFace().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType p) {
        return false;
    }
}
