package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.SmokyGrillingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class SmokingRackBlockEntity extends BlockEntity {
    public ItemStack stack1, stack2, result1, result2;
    public int tick1, time1, tick2, time2;
    public boolean isSmoky1, isSmoky2;
    public ItemStack smoky1, smoky2;

    public SmokingRackBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Smoking_Rack_BlockEntity, pos, state);
        this.stack1 = this.stack2 = this.result1 = this.result2 = ItemStack.EMPTY;
        this.tick1 = this.time1 = this.tick2 = this.time2 = 0;
        this.isSmoky1 = this.isSmoky2 = true;
    }

    public void matchRecipe(int slot) {
        Item item = (slot == 1 ? this.stack1 : this.stack2).getItem();
        if (this.world == null || this.world.isClient) return;
        this.toInitialChunkDataNbt();
        Optional<SmokyGrillingRecipe> recipe = this.world.getRecipeManager().getFirstMatch(
                CateRecipes.Smoky_Grilling_RecipeType, new SimpleInventory(new ItemStack(item)), this.world);
        if (recipe.isPresent()) {
            if (slot == 1) {
                this.result1 = recipe.get().result.copy();
                this.smoky1 = !recipe.get().moreSmoke.isEmpty() ? recipe.get().moreSmoke.copy() : this.result1.copy();
                this.time1 = recipe.get().cookTime;
            } else {
                this.result2 = recipe.get().result.copy();
                this.smoky2 = !recipe.get().moreSmoke.isEmpty() ? recipe.get().moreSmoke.copy() : this.result2.copy();
                this.time2 = recipe.get().cookTime;
            }
        }
    }

    public boolean insertItem(ItemStack itemStack) {
        if (this.stack1.isEmpty()) {
            this.stack1 = itemStack.copy();
            this.stack1.setCount(1);
            itemStack.decrement(1);
            this.matchRecipe(1);
            return true;
        } else if (this.stack2.isEmpty()) {
            this.stack2 = itemStack.copy();
            this.stack2.setCount(1);
            itemStack.decrement(1);
            this.matchRecipe(2);
            return true;
        }
        this.markDirty();
        return false;
    }

    public ItemStack getItem() {
        ItemStack itemStack = ItemStack.EMPTY;
        if (!this.stack1.isEmpty()) {
            itemStack = this.stack1.copy();
        } else if (!this.stack2.isEmpty()) {
            itemStack = this.stack2.copy();
        }
        return itemStack;
    }

    public ItemStack pickItem() {
        ItemStack itemStack = ItemStack.EMPTY;
        if (!this.stack1.isEmpty()) {
            itemStack = this.stack1.copy();
            this.stack1 = ItemStack.EMPTY;
        } else if (!this.stack2.isEmpty()) {
            itemStack = this.stack2.copy();
            this.stack2 = ItemStack.EMPTY;
        }
        this.markDirty();
        return itemStack;
    }
    
    public static boolean isOnFire(World world, BlockPos pos) {
        BlockState campfire = world.getBlockState(pos.down());
        return (campfire.isOf(Blocks.CAMPFIRE) || campfire.isOf(Blocks.SOUL_CAMPFIRE)) && campfire.get(CampfireBlock.LIT);
    }

    public static boolean isOnSmokyFire(World world, BlockPos pos) {
        BlockState campfire = world.getBlockState(pos.down());
        return isOnFire(world, pos) && campfire.get(CampfireBlock.SIGNAL_FIRE);
    }

    public boolean isFull() {
        return !this.stack1.isEmpty() && !this.stack2.isEmpty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof SmokingRackBlockEntity entity && isOnFire(world, pos)) {
            boolean smoky = isOnSmokyFire(world, pos);
            if (!smoky) entity.isSmoky1 = entity.isSmoky2 = false;
            if (!entity.stack1.isEmpty() && !entity.result1.isEmpty()) {
                if (entity.tick1 >= entity.time1) {
                    entity.stack1 = entity.isSmoky1 ? entity.smoky1.copy() : entity.result1.copy();
                    entity.smoky1 = entity.result1 = ItemStack.EMPTY;
                    entity.matchRecipe(1);
                } else {
                    entity.tick1++;
                }
            } else {
                entity.tick1 = entity.time1 = 0;
                entity.isSmoky1 = true;
            }
            if (!entity.stack2.isEmpty() && !entity.result2.isEmpty()) {
                if (entity.tick2 >= entity.time2) {
                    entity.stack2 = entity.isSmoky2 ? entity.smoky2.copy() : entity.result2.copy();
                    entity.smoky2 = entity.result2 = ItemStack.EMPTY;
                    entity.matchRecipe(2);
                } else {
                    entity.tick2++;
                }
            } else {
                entity.tick2 = entity.time2 = 0;
                entity.isSmoky2 = true;
            }
            entity.markDirty();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        DefaultedList<ItemStack> list = DefaultedList.ofSize(2, ItemStack.EMPTY);
        Inventories.readNbt(nbt, list);
        this.stack1 = list.get(0).copy();
        this.stack2 = list.get(1).copy();
        this.matchRecipe(1);
        this.matchRecipe(2);
        this.tick1 = nbt.getInt("tick1");
        this.tick2 = nbt.getInt("tick2");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        DefaultedList<ItemStack> list = DefaultedList.ofSize(2, ItemStack.EMPTY);
        list.set(0, this.stack1.copy());
        list.set(1, this.stack2.copy());
        Inventories.writeNbt(nbt, list);
        nbt.putInt("tick1", this.tick1);
        nbt.putInt("tick2", this.tick2);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    private void updateListeners() {
        this.markDirty();
        if (this.world == null) return;
        this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        this.updateListeners();
        NbtCompound nbt = new NbtCompound();
        this.writeNbt(nbt);
        return nbt;
    }

}
