package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.api.CateBlockEntityInventory;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.PotCookingRecipe;
import com.everlastsino.cate.screen.screens.SaucepanScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

enum SaucepanSlots {
    INGREDIENT_1,
    INGREDIENT_2,
    INGREDIENT_3,
    INGREDIENT_4,
    INGREDIENT_5,
    INGREDIENT_6,
    RESULT_1,
    RESULT_2,
    RESULT_3,
    CONTAINER,
    WATER_CONTAINER
}

public class SaucepanBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, CateBlockEntityInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(11, ItemStack.EMPTY);
    private Ingredient latestIngredient;
    private ItemStack latestContainer;
    private Ingredient latestResult;
    private float experience;
    private int cookingTime;
    public boolean isCooking;
    public boolean isBeating;
    public int cookingTick;
    public int temperature;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return SaucepanBlockEntity.this.isCooking ? 1 : 0;
                }
                case 1 -> {
                    return SaucepanBlockEntity.this.cookingTick;
                }
                case 2 -> {
                    return SaucepanBlockEntity.this.cookingTime;
                }
                case 3 -> {
                    return SaucepanBlockEntity.this.isBeating ? 1 : 0;
                }
                case 4 -> {
                    return SaucepanBlockEntity.this.temperature;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SaucepanBlockEntity.this.isCooking = value >= 1;
                case 1 -> SaucepanBlockEntity.this.cookingTick = value;
                case 2 -> SaucepanBlockEntity.this.cookingTime = value;
                case 3 -> SaucepanBlockEntity.this.isBeating = value >= 1;
                case 4 -> SaucepanBlockEntity.this.temperature = value;
            }
        }

        @Override
        public int size() {
            return 5;
        }
    };

    public SaucepanBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Saucepan_BlockEntity, pos, state);
        this.isCooking = false;
        this.cookingTick = 0;
        this.experience = this.cookingTime = 0;
        this.latestIngredient = this.latestResult = Ingredient.EMPTY;
        this.latestContainer = ItemStack.EMPTY;
    }

    public DefaultedList<ItemStack> getIngredient() {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(6, ItemStack.EMPTY);
        ingredients.set(0, this.inventory.get(SaucepanSlots.INGREDIENT_1.ordinal()));
        ingredients.set(1, this.inventory.get(SaucepanSlots.INGREDIENT_2.ordinal()));
        ingredients.set(2, this.inventory.get(SaucepanSlots.INGREDIENT_3.ordinal()));
        ingredients.set(3, this.inventory.get(SaucepanSlots.INGREDIENT_4.ordinal()));
        ingredients.set(4, this.inventory.get(SaucepanSlots.INGREDIENT_5.ordinal()));
        ingredients.set(5, this.inventory.get(SaucepanSlots.INGREDIENT_6.ordinal()));
        return ingredients;
    }

    public void eraseIngredient() {
        this.inventory.get(SaucepanSlots.INGREDIENT_1.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_1.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_2.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_2.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_3.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_3.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_4.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_4.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_5.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_5.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_6.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_6.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.CONTAINER.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.CONTAINER.ordinal()).getCount() - 1));
    }

    public DefaultedList<ItemStack> getResults() {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(3, ItemStack.EMPTY);
        ingredients.set(0, this.inventory.get(SaucepanSlots.RESULT_1.ordinal()));
        ingredients.set(1, this.inventory.get(SaucepanSlots.RESULT_2.ordinal()));
        ingredients.set(2, this.inventory.get(SaucepanSlots.RESULT_3.ordinal()));
        return ingredients;
    }

    public void insertResults(Ingredient result) {
        List<ItemStack> itemStacks = List.of(result.getMatchingStacks());
        for (int i = 0; i < 3 && i < itemStacks.size(); ++i) {
            int p = i + SaucepanSlots.RESULT_1.ordinal();
            this.inventory.set(p, itemStacks.get(i).copy());
        }
    }

    public ItemStack getContainer() {
        return this.inventory.get(SaucepanSlots.CONTAINER.ordinal());
    }

    public ItemStack getWaterContainer() {
        return this.inventory.get(SaucepanSlots.WATER_CONTAINER.ordinal());
    }

    public void eraseWater() {
        if (this.inventory.get(SaucepanSlots.WATER_CONTAINER.ordinal()).isOf(Items.WATER_BUCKET)) {
            this.inventory.set(SaucepanSlots.WATER_CONTAINER.ordinal(), new ItemStack(Items.BUCKET));
        }
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

    public void updateBeatingSituation() {
        this.isBeating = this.world != null &&
                (this.world.getBlockState(this.pos.down()).isOf(Blocks.CAMPFIRE) || this.world.getBlockState(this.pos.down()).isOf(Blocks.SOUL_CAMPFIRE)) &&
                this.world.getBlockState(this.pos.down()).get(CampfireBlock.LIT);
        this.temperature = this.isBeating ? 75 : 20;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, float experience) {
        int i = MathHelper.floor(experience);
        float f = MathHelper.fractionalPart(experience);
        if (f != 0.0f && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrbEntity.spawn(world, pos, i);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof SaucepanBlockEntity entity) {
            entity.updateBeatingSituation();
            if (entity.cookingTime == 0) {
                List<PotCookingRecipe> recipes = world.getRecipeManager().listAllOfType(CateRecipes.Pot_Cooking_RecipeType);
                for (PotCookingRecipe recipe : recipes) {
                    if (recipe.check(entity.getIngredient(), entity.getContainer())) {
                        entity.latestIngredient = recipe.ingredient;
                        entity.latestContainer = recipe.container.copy();
                        entity.latestResult = recipe.result;
                        entity.experience = recipe.experience;
                        entity.cookingTime = recipe.cookTime;
                        entity.markDirty();
                        break;
                    }
                }
            }
            if (entity.getWaterContainer().isOf(Items.WATER_BUCKET) && entity.getContainer().isOf(entity.latestContainer.getItem()) &&
                    entity.getResults().get(0).isOf(Items.AIR) && entity.getResults().get(1).isOf(Items.AIR) && entity.getResults().get(2).isOf(Items.AIR) &&
                    entity.compareList(List.of(entity.latestIngredient.getMatchingStacks()), entity.getIngredient()) && entity.isBeating) {
                if (entity.cookingTime == entity.cookingTick) {
                    entity.insertResults(entity.latestResult);
                    entity.eraseWater();
                    entity.eraseIngredient();
                    dropExperience((ServerWorld) world, Vec3d.ofCenter(pos), entity.experience);
                    entity.experience = 0;
                    return;
                }
                entity.isCooking = true;
                entity.cookingTick++;
            } else {
                entity.cookingTick = entity.cookingTime = 0;
                entity.isCooking = false;
            }
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new SaucepanScreenHandler(syncId, inventory, this, this.propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.isCooking = nbt.getBoolean("is_cooking");
        this.cookingTick = nbt.getInt("cooking_tick");
        this.cookingTime = nbt.getInt("cooking_time");
        this.experience = nbt.getFloat("experience");
        if (this.world != null) {
            List<PotCookingRecipe> recipes = this.world.getRecipeManager().listAllOfType(CateRecipes.Pot_Cooking_RecipeType);
            for (PotCookingRecipe recipe : recipes) {
                if (recipe.check(this.getIngredient(), this.getContainer())) {
                    this.latestIngredient = recipe.ingredient;
                    this.latestResult = recipe.result;
                    this.experience = recipe.experience;
                    this.cookingTime = recipe.cookTime;
                    break;
                }
            }
        }
        this.updateBeatingSituation();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putBoolean("is_cooking", this.isCooking);
        nbt.putInt("cooking_tick", this.cookingTick);
        nbt.putInt("cooking_time", this.cookingTime);
        nbt.putFloat("experience", this.experience);
    }

}
