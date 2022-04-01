package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.api.enums.RollingPinTypes;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.item.CateItems;
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
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class CuttingBoardBlockEntity extends BlockEntity{
    public ItemStack itemStack;
    public int requireSteps;
    public ItemStack result;
    public ItemStack tool;
    public int damageTool;
    public int step;
    public boolean matchedRecipe;
    public RollingPinTypes lastType;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Cutting_Board_BlockEntity, pos, state);
        this.itemStack = ItemStack.EMPTY;
        this.clearRecipe();
        this.lastType = null;
    }

    public void clearRecipe() {
        this.result = this.tool = ItemStack.EMPTY;
        this.requireSteps = this.step = 0;
        this.matchedRecipe = false;
    }

    public boolean matchRecipe() {
        if (this.world == null || this.itemStack.isEmpty()) return false;
        Optional<CuttingRecipe> recipe = this.world.getRecipeManager().getFirstMatch(
                CateRecipes.Cutting_RecipeType, new SimpleInventory(this.itemStack), this.world);
        if (recipe.isEmpty()) return false;
        CuttingRecipe newRecipe = recipe.get();
        this.requireSteps = newRecipe.steps;
        this.result = newRecipe.result.copy();
        this.tool = newRecipe.tool.copy();
        this.damageTool = newRecipe.damageTool;
        this.step = 0;
        this.matchedRecipe = true;
        return true;
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

    public boolean operate() {
        if (!this.hasItem()) return false;
        if (!this.matchedRecipe && !this.matchRecipe()) {
            return false;
        }
        if (++this.step == this.requireSteps) {
            this.itemStack = this.result.copy();
            this.clearRecipe();
        }
        this.updateListeners();
        return true;
    }

    public void matchDoughRecipe(RollingPinTypes type) {
        this.lastType = type;
        this.requireSteps = type.steps;
        this.result = RollingPinTypes.getStackFromType(type);
        this.tool = new ItemStack(CateItems.Rolling_Pin);
        this.damageTool = 1;
        this.step = 0;
        this.matchedRecipe = true;
    }

    public boolean operateWithDough(RollingPinTypes type) {
        if (!this.hasItem() || this.itemStack.isOf(RollingPinTypes.getStackFromType(type).getItem())) {
            return false;
        }
        if (!this.matchedRecipe || this.lastType == null || this.lastType.equals(type)) {
            this.matchDoughRecipe(type);
        }
        if (++this.step == this.requireSteps) {
            this.itemStack = this.result;
            System.out.println(this.result.copy());
            this.clearRecipe();
        }
        this.updateListeners();
        return true;
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
