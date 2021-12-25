package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.item.CateItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class OsmanthusTreeLeavesBlock extends LeavesBlock {
    public static final IntProperty AGE = Properties.AGE_1;
    public static final BooleanProperty GROWABLE = BooleanProperty.of("growable");

    public OsmanthusTreeLeavesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(GROWABLE,false).with(DISTANCE, 7).with(PERSISTENT, false).with(AGE,0));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(PERSISTENT) && state.get(GROWABLE) && state.get(AGE) < 1;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (random.nextInt(world.isRaining() ? 30 : 60) == 0){
            world.setBlockState(pos, this.getDefaultState().with(AGE, 1));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (state.get(PERSISTENT) && state.get(GROWABLE) && state.get(AGE) == 1){
            if (itemStack.isOf(Items.SHEARS)){
                itemStack.setDamage(itemStack.getDamage() - 1);
                if (itemStack.getDamage() <= 0){
                    itemStack.decrement(1);
                }
                dropOsmanthus(world, pos);
            }
            dropOsmanthus(world, pos);
            world.setBlockState(pos, this.getDefaultState().with(AGE, 0));
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    private void dropOsmanthus(World world, BlockPos pos){
        Random random = new Random();
        dropStack(world, pos, new ItemStack(CateItems.Osmanthus, 1 + random.nextInt(3)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE, GROWABLE);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if (state.get(PERSISTENT) && state.get(GROWABLE) && state.get(AGE) == 1){
            dropOsmanthus(world, pos);
        }
    }
}
