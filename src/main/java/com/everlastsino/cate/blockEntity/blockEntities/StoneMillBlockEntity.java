package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.CateSoundEvents;
import com.everlastsino.cate.api.CateBlockEntityInventory;
import com.everlastsino.cate.api.enums.StoneMillSlots;
import com.everlastsino.cate.block.blocks.StoneMillBlock;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.MillingRecipe;
import com.everlastsino.cate.screen.screens.StoneMillScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoneMillBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, CateBlockEntityInventory, SidedInventory {
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

    public void insertResults(Ingredient result) {
        List<ItemStack> itemStacks = List.of(result.getMatchingStacks());
        for (ItemStack itemStack : itemStacks) {
            boolean success = false;
            for (int i = StoneMillSlots.RESULT_1.ordinal(); i <= StoneMillSlots.RESULT_3.ordinal(); ++i) {
                ItemStack itemInInv = this.inventory.get(i);
                if (itemInInv.isEmpty()) {
                    this.inventory.set(i, itemStack.copy());
                    success = true;
                    break;
                } else if (itemInInv.isOf(itemStack.getItem()) && itemInInv.getCount() < itemStack.getItem().getMaxCount()) {
                    this.inventory.get(i).increment(1);
                    success = true;
                    break;
                }
            }
            if (!success) {
                ItemScatterer.spawn(this.world, this.pos, new SimpleInventory(itemStack.copy()));
            }
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
        if (this.world != null && !this.world.isClient && this.getContainer().isOf(this.latestContainer.getItem())
                && this.compareList(List.of(this.latestIngredient.getMatchingStacks()), this.getIngredient())) {
            this.step++;
            this.playSound();
            if (this.requireRounds * 8 <= this.step) {
                this.eraseIngredient();
                this.insertResults(this.latestResult);
            }
        } else {
            this.requireRounds = this.step = 0;
        }
        this.setBlockState();
    }

    public void playSound() {
        if (this.world == null) return;
        this.world.playSound(null, this.pos, CateSoundEvents.Stone_Mill_Working, SoundCategory.BLOCKS, 1, 1);
    }

    public void setBlockState() {
        if (this.world == null) return;
        this.world.setBlockState(this.pos,
                this.world.getBlockState(this.pos).with(StoneMillBlock.STEP, this.step % 8 + 1));
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
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


    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.UP) {
            return StoneMillSlots.UP_SLOTS;
        } else if (side == Direction.DOWN) {
            return StoneMillSlots.DOWN_SLOTS;
        }
        return StoneMillSlots.SIDE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot >= StoneMillSlots.INGREDIENT_1.ordinal() && slot <= StoneMillSlots.INGREDIENT_3.ordinal() || slot == StoneMillSlots.CONTAINER.ordinal();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot >= StoneMillSlots.RESULT_1.ordinal() && slot <= StoneMillSlots.RESULT_3.ordinal();
    }
}
