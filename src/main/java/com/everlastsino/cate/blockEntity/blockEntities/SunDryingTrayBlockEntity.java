package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.SunDryingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class SunDryingTrayBlockEntity extends BlockEntity {
    public DefaultedList<ItemStack> itemStacks;
    public DefaultedList<ItemStack> results;
    public int[] timer;
    public int[] dryingTime;

    public SunDryingTrayBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Sun_Drying_Tray_BlockEntity, pos, state);
        this.itemStacks = DefaultedList.ofSize(4, ItemStack.EMPTY);
        this.results = DefaultedList.ofSize(4, ItemStack.EMPTY);
        this.timer = new int[]{0, 0, 0, 0};
        this.dryingTime = new int[]{0, 0, 0, 0};
    }

    public boolean insertItem(ItemStack handStack) {
        for (int i = 0; i < 4; ++i) {
            if (this.itemStacks.get(i).isEmpty()) {
                this.itemStacks.set(i, handStack.copy());
                this.itemStacks.get(i).setCount(1);
                handStack.decrement(1);
                this.matchRecipe(i);
                this.toInitialChunkDataNbt();
                return true;
            }
        }
        return false;
    }

    public ItemStack pickItem() {
        for (int i = 0; i < 4; ++i) {
            if (!this.itemStacks.get(i).isEmpty()) {
                ItemStack pickStack = this.itemStacks.get(i).copy();
                this.itemStacks.set(i, ItemStack.EMPTY);
                this.clearRecipe(i);
                return pickStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getItem() {
        for (int i = 0; i < 4; ++i) {
            if (!this.itemStacks.get(i).isEmpty()) {
                return this.itemStacks.get(i).copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean isFull() {
        for (ItemStack itemStack : this.itemStacks) {
            if (itemStack.isEmpty()) return false;
        }
        return true;
    }

    public void matchRecipe(int slot) {
        ItemStack itemStack = this.itemStacks.get(slot);
        if (this.world == null || this.world.isClient || itemStack.isEmpty()) return;
        Optional<SunDryingRecipe> recipe = this.world.getRecipeManager().getFirstMatch(
                CateRecipes.Sun_Drying_RecipeType, new SimpleInventory(itemStack), this.world);
        if (recipe.isPresent()) {
            this.results.set(slot, recipe.get().result.copy());
            this.dryingTime[slot] = recipe.get().dryingTime;
        }
    }

    public void clearRecipe(int slot) {
        this.dryingTime[slot] = 0;
        this.timer[slot] = 0;
        this.results.set(slot, ItemStack.EMPTY);
    }

    public boolean isUnderSun() {
        return this.world != null && this.world.getLightLevel(this.pos) >= 15 && this.world.isDay() && !this.world.isRaining();
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof SunDryingTrayBlockEntity entity) {
            if (entity.isUnderSun()) {
                for (int i = 0; i < 4; ++i) {
                    ItemStack itemStack = entity.itemStacks.get(i), result = entity.results.get(i);
                    if (!itemStack.isEmpty() && !result.isEmpty()) {
                        if (entity.timer[i] >= entity.dryingTime[i]) {
                            entity.itemStacks.set(i, result.copy());
                            entity.clearRecipe(i);
                            entity.matchRecipe(i);
                            entity.toInitialChunkDataNbt();
                        } else {
                            entity.timer[i]++;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.itemStacks);
        this.timer = nbt.getIntArray("timer");
        for (int i = 0; i < 4; ++i) this.matchRecipe(i);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.itemStacks);
        nbt.putIntArray("timer", this.timer);
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
