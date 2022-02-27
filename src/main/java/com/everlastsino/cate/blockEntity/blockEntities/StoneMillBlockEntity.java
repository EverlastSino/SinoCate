package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.api.CateBlockEntityInventory;
import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.blocks.StoneMillBlock;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.MillingRecipe;
import com.everlastsino.cate.screen.screens.SaucepanScreenHandler;
import com.everlastsino.cate.screen.screens.StoneMillScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

enum StoneMillSlots {
    INGREDIENT_1,
    INGREDIENT_2,
    INGREDIENT_3,
    RESULT_1,
    RESULT_2,
    RESULT_3,
    CONTAINER
}

public class StoneMillBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, CateBlockEntityInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7, ItemStack.EMPTY);
    private Ingredient latestIngredient;
    private ItemStack latestContainer;
    private Ingredient latestResult;
    private int requireRounds;
    public int step;
    
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return StoneMillBlockEntity.this.requireRounds;
                }
                case 1 -> {
                    return StoneMillBlockEntity.this.step;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> StoneMillBlockEntity.this.requireRounds = value;
                case 1 -> StoneMillBlockEntity.this.step = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public StoneMillBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Stone_Mill_BlockEntity, pos, state);
        this.requireRounds = this.step = 0;
        this.latestIngredient = this.latestResult = Ingredient.EMPTY;
        this.latestContainer = ItemStack.EMPTY;
    }

    public DefaultedList<ItemStack> getIngredient() {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(3, ItemStack.EMPTY);
        ingredients.set(0, this.inventory.get(StoneMillSlots.INGREDIENT_1.ordinal()));
        ingredients.set(1, this.inventory.get(StoneMillSlots.INGREDIENT_2.ordinal()));
        ingredients.set(2, this.inventory.get(StoneMillSlots.INGREDIENT_3.ordinal()));
        return ingredients;
    }

    public void eraseIngredient() {
        for (int i = 0; i < 3; ++i) {
            ItemStack stack = this.inventory.get(i);
            if (stack.isEmpty() || stack.getCount() <= 0) continue;
            if (stack.getMaxCount() == 1){
                ItemStack remainder = stack.getItem().hasRecipeRemainder() ? new ItemStack(stack.getItem().getRecipeRemainder()) : ItemStack.EMPTY;
                this.inventory.set(i, remainder);
            } else {
                stack.decrement(1);
            }
        }
        this.inventory.get(StoneMillSlots.CONTAINER.ordinal()).setCount(Math.max(0, this.inventory.get(StoneMillSlots.CONTAINER.ordinal()).getCount() - 1));
    }

    public DefaultedList<ItemStack> getResults() {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(3, ItemStack.EMPTY);
        ingredients.set(0, this.inventory.get(StoneMillSlots.RESULT_1.ordinal()));
        ingredients.set(1, this.inventory.get(StoneMillSlots.RESULT_2.ordinal()));
        ingredients.set(2, this.inventory.get(StoneMillSlots.RESULT_3.ordinal()));
        return ingredients;
    }

    public void insertResults(Ingredient result) {
        List<ItemStack> itemStacks = List.of(result.getMatchingStacks());
        for (int i = 0; i < 3 && i < itemStacks.size(); ++i) {
            int p = i + StoneMillSlots.RESULT_1.ordinal();
            this.inventory.set(p, itemStacks.get(i).copy());
        }
        this.requireRounds = this.step = 0;
    }

    public ItemStack getContainer() {
        return this.inventory.get(StoneMillSlots.CONTAINER.ordinal());
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public boolean compareList(List<ItemStack> itemStacks1, List<ItemStack> itemStacks2) {
        int realSize = 0;
        for (ItemStack stack1 : itemStacks2) {
            realSize += stack1.isOf(Items.AIR) ? 0 : 1;
        }
        if (realSize != itemStacks1.size()) return false;
        for (ItemStack stack : itemStacks1) {
            boolean has = false;
            for (ItemStack stack1 : itemStacks2) {
                if (stack.getItem() == stack1.getItem()) {
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }
        return true;
    }

    private void matchRecipe() {
        if (this.world == null) return;
        List<MillingRecipe> recipes = this.world.getRecipeManager().listAllOfType(CateRecipes.Milling_RecipeType);
        for (MillingRecipe recipe : recipes) {
            if (recipe.check(this.getIngredient(), this.getContainer())) {
                this.latestIngredient = recipe.ingredient;
                this.latestContainer = recipe.container.copy();
                this.latestResult = recipe.result;
                this.requireRounds = recipe.rounds;
                this.markDirty();
                this.setBlockState();
                break;
            }
        }
    }

    public void operate() {
        if (this.step == 0) {
           this.matchRecipe();
        }
        if (this.getContainer().isOf(this.latestContainer.getItem()) &&
                this.getResults().get(0).isOf(Items.AIR) && this.getResults().get(1).isOf(Items.AIR) && this.getResults().get(2).isOf(Items.AIR) &&
                this.compareList(List.of(this.latestIngredient.getMatchingStacks()), this.getIngredient())) {
            this.step++;
            if (this.requireRounds * 8 <= this.step) {
                this.eraseIngredient();
                this.insertResults(this.latestResult);
            }
        } else {
            this.requireRounds = this.step = 0;
        }
        this.setBlockState();
    }

    public void setBlockState() {
        if (this.world == null) return;
        this.world.setBlockState(this.pos,
                this.world.getBlockState(this.pos).with(StoneMillBlock.STEP, this.step % 8 + 1));
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new StoneMillScreenHandler(syncId, inventory, this, this.propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.requireRounds = nbt.getInt("require_rounds");
        this.step = nbt.getInt("step");
        this.matchRecipe();
        this.setBlockState();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("require_rounds", this.requireRounds);
        nbt.putInt("step", this.step);
    }

}
