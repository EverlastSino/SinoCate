package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.CuttingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CuttingBoardBlockEntity extends BlockEntity {
    public ItemStack itemStack;
    public int requireSteps;
    public ItemStack result;
    public ItemStack tool;
    public int damageTool;
    public int step;
    public boolean matchedRecipe, matchedCorrect;
    public List<CuttingRecipe> recipes = new ArrayList<>();
    public ItemStack lastTool;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Cutting_Board_BlockEntity, pos, state);
        this.itemStack = ItemStack.EMPTY;
        this.lastTool = ItemStack.EMPTY;
        this.clearRecipe();
    }

    public void clearRecipe() {
        this.result = this.tool = this.lastTool = ItemStack.EMPTY;
        this.requireSteps = this.step = 0;
        this.matchedRecipe = this.matchedCorrect = false;
        this.recipes.clear();
    }

    public boolean matchRecipe() {
        if (this.world == null || this.itemStack.isEmpty()) return false;
        this.recipes = this.world.getRecipeManager().getAllMatches(
                CateRecipes.Cutting_RecipeType, new SimpleInventory(this.itemStack), this.world);
        this.matchedRecipe = !this.recipes.isEmpty();
        return this.matchedRecipe;
    }

    public boolean matchCorrectRecipe(ItemStack tool) {
        for (CuttingRecipe recipe : this.recipes) {
            if (recipe.tool.isOf(tool.getItem())) {
                this.requireSteps = recipe.steps;
                this.result = recipe.result.copy();
                this.tool = recipe.tool.copy();
                this.damageTool = recipe.damageTool;
                this.step = tool.isOf(this.lastTool.getItem()) ? this.step : 0;
                this.matchedCorrect = true;
                return true;
            }
        }
        return false;
    }

    public boolean hasItem() {
        return !this.itemStack.isEmpty();
    }

    public boolean insertStack(ItemStack stackInHand, boolean creative) {
        if (this.hasItem()) return false;
        this.itemStack = stackInHand.copy();
        this.itemStack.setCount(1);
        if (!creative) stackInHand.decrement(1);
        this.matchRecipe();
        this.updateListeners();
        return true;
    }

    public ItemStack popStack() {
        ItemStack stack = this.itemStack.copy();
        this.itemStack = ItemStack.EMPTY;
        this.clearRecipe();
        this.updateListeners();
        return stack;
    }

    public boolean operate(ItemStack tool) {
        if (!this.hasItem() || (!this.matchedRecipe && !this.matchRecipe())) return false;
        if (this.matchCorrectRecipe(tool)) {
            this.lastTool = tool.copy();
            if (++this.step == this.requireSteps) {
                this.itemStack = this.result.copy();
                if (this.itemStack.getCount() > 1) {
                    ItemStack dropper = this.itemStack.copy();
                    dropper.setCount(this.itemStack.getCount() - 1);
                    ItemScatterer.spawn(this.world, this.pos, new SimpleInventory(dropper));
                    this.itemStack.setCount(1);
                }
                this.clearRecipe();
                return true;
            }
            this.updateListeners();
            return true;
        }
        this.matchedCorrect = false;
        this.updateListeners();
        return false;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        DefaultedList<ItemStack> list = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.readNbt(nbt, list);
        this.itemStack = list.get(0).copy();
        this.step = nbt.getInt("step");
        this.matchRecipe();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        DefaultedList<ItemStack> list = DefaultedList.ofSize(1, this.itemStack.copy());
        Inventories.writeNbt(nbt, list);
        nbt.putInt("step", this.step);
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
        DefaultedList<ItemStack> list = DefaultedList.ofSize(1, this.itemStack.copy());
        Inventories.writeNbt(nbt, list);
        return nbt;
    }

}
