package com.everlastsino.cate.block.blocks;

import com.everlastsino.cate.blockEntity.blockEntities.WoodenSteamerBlockEntity;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.SteamingRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Optional;

public class WoodenSteamerBlock extends Block implements BlockEntityProvider {
    public static final EnumProperty<SlabType> TYPE;
    public static BooleanProperty LIT;
    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape TOP_SHAPE;

    public WoodenSteamerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TYPE, SlabType.BOTTOM).with(LIT, false));
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        dropStacks(state, world, pos, blockEntity, player, stack);
        if(blockEntity instanceof WoodenSteamerBlockEntity entity){
            for(int i = 0; i < 4; i++){
                if(!entity.downItems.get(i).isEmpty()){
                    dropStack(world, pos, entity.downItems.get(i));
                }
                if(!entity.upItems.get(i).isEmpty()){
                    dropStack(world, pos, entity.upItems.get(i));
                }
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenSteamerBlockEntity(pos, state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world != null && !world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WoodenSteamerBlockEntity entity){
                if (!itemStack.isEmpty() && hand == Hand.MAIN_HAND){
                    Inventory inventory = new SimpleInventory(itemStack);
                    Optional<SteamingRecipe> itemRecipe = world.getRecipeManager().getFirstMatch(CateRecipes.Steaming_RecipeType, inventory, world);
                    if (itemRecipe.isPresent()){
                        ItemStack itemStack1 = itemRecipe.map((SteamingRecipe) -> SteamingRecipe.craft(inventory)).orElse(itemStack);
                        int cookTime = itemRecipe.get().cookTime;
                        if (entity.insertItem(pos, new ItemStack(itemStack.getItem()), itemStack1, cookTime)){
                            if (!player.getAbilities().creativeMode){
                                itemStack.decrement(1);
                            }
                            return ActionResult.SUCCESS;
                        }
                    }
                } else if (hand == Hand.MAIN_HAND){
                    ItemStack pickStack = entity.pickItem(pos, true);
                    if (!pickStack.isEmpty()){
                        dropStack(world, pos.up(), pickStack);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, LIT);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> VoxelShapes.union(TOP_SHAPE, BOTTOM_SHAPE);
            case TOP -> TOP_SHAPE;
            default -> BOTTOM_SHAPE;
        };
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return blockState.with(TYPE, SlabType.DOUBLE);
        } else {
            return this.getDefaultState().with(TYPE, SlabType.BOTTOM);
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = state.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.isOf(this.asItem())) {
            if (context.canReplaceExisting()) {
                boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5D;
                Direction direction = context.getSide();
                if (slabType == SlabType.BOTTOM) {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? WoodenSteamerBlockEntity::clientTick : WoodenSteamerBlockEntity::tick;
    }

    static {
        TYPE = Properties.SLAB_TYPE;
        LIT = Properties.LIT;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.01D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}