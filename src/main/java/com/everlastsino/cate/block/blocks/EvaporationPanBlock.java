package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.item.CateItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class EvaporationPanBlock extends Block {

    public static final IntProperty LEVEL = Properties.AGE_3;
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            Block.createCuboidShape(0.0D,0.0D,0.0D,16.0D,8.0D,16.0D),
            Block.createCuboidShape(1.0D,1.0D,1.0D,15.0D,8.0D,15.0D),
            BooleanBiFunction.ONLY_FIRST);

    public EvaporationPanBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int level = state.get(LEVEL);
        if (!world.isClient && entity.isOnFire() && level > 0) {
            entity.extinguish();
            entity.setOnFire(false);
            world.setBlockState(pos,state.with(LEVEL,level - 1));
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        boolean isCooking = blockState.isOf(Blocks.FIRE) || blockState.isOf(Blocks.SOUL_FIRE) || blockState.isOf(Blocks.CAMPFIRE) || blockState.isOf(Blocks.SOUL_CAMPFIRE);
        boolean isUnderSky = world.isSkyVisible(pos) && world.isDay();
        int level = state.get(LEVEL);
        if (level > 0 && level < 3 && random.nextInt(Math.max(50 - (isCooking ? 20 : 0) - (isUnderSky ? (world.isRaining() ? -60 : 20) : 0), 0)) == 0) {
            world.setBlockState(pos, state.with(LEVEL, level + 1));
        }
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        int level = state.get(LEVEL);
        if(itemStack.isOf(Items.WATER_BUCKET) && level == 0){
            if(!player.getAbilities().creativeMode){
                player.setStackInHand(hand, new ItemStack(Items.BUCKET));
            }
            world.setBlockState(pos, state.with(LEVEL, 1));
            return ActionResult.SUCCESS;
        }else if(itemStack.isOf(Items.BUCKET) && level > 0){
            ItemStack returnItem = ItemStack.EMPTY;
            if(level < 3 && !player.getAbilities().creativeMode){
                returnItem = new ItemStack(Items.WATER_BUCKET);
                itemStack.decrement(1);
            }else if(level == 3 && !player.getAbilities().creativeMode){
                returnItem = new ItemStack(CateItems.Salt_Bucket);
                itemStack.decrement(1);
            }
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, returnItem);
            } else {
                if (!player.getInventory().insertStack(returnItem)) {
                    player.dropItem(returnItem, false);
                }
            }
            world.setBlockState(pos, state.with(LEVEL, 0));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

}
