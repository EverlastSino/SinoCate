package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.api.CateBlockEntityInventory;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.recipe.CateRecipes;
import com.everlastsino.cate.recipe.recipes.PotCookingRecipe;
import com.everlastsino.cate.screen.screens.SaucepanScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickScheduler;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

enum SaucepanSlots{
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
    private boolean isCooking;
    private int cookingTick;

    public SaucepanBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Saucepan_BlockEntity, pos, state);
        this.isCooking = false;
        this.cookingTick = 0;
        this.experience = this.cookingTime = 0;
        this.latestIngredient = this.latestResult = Ingredient.EMPTY;
        this.latestContainer = ItemStack.EMPTY;
    }

    public DefaultedList<ItemStack> getIngredient(){
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(6, ItemStack.EMPTY);
        ingredients.set(0, this.inventory.get(SaucepanSlots.INGREDIENT_1.ordinal()));
        ingredients.set(1, this.inventory.get(SaucepanSlots.INGREDIENT_2.ordinal()));
        ingredients.set(2, this.inventory.get(SaucepanSlots.INGREDIENT_3.ordinal()));
        ingredients.set(3, this.inventory.get(SaucepanSlots.INGREDIENT_4.ordinal()));
        ingredients.set(4, this.inventory.get(SaucepanSlots.INGREDIENT_5.ordinal()));
        ingredients.set(5, this.inventory.get(SaucepanSlots.INGREDIENT_6.ordinal()));
        return ingredients;
    }

    public void eraseIngredient(){
        this.inventory.get(SaucepanSlots.INGREDIENT_1.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_1.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_2.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_2.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_3.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_3.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_4.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_4.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_5.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_5.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.INGREDIENT_6.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.INGREDIENT_6.ordinal()).getCount() - 1));
        this.inventory.get(SaucepanSlots.CONTAINER.ordinal()).setCount(Math.max(0, this.inventory.get(SaucepanSlots.CONTAINER.ordinal()).getCount() - 1));
    }

    public DefaultedList<ItemStack> getResults(){
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(3, ItemStack.EMPTY);
        ingredients.set(0, this.inventory.get(SaucepanSlots.RESULT_1.ordinal()));
        ingredients.set(1, this.inventory.get(SaucepanSlots.RESULT_2.ordinal()));
        ingredients.set(2, this.inventory.get(SaucepanSlots.RESULT_3.ordinal()));
        return ingredients;
    }

    public void insertResults(Ingredient result){
        List<ItemStack> itemStacks = List.of(result.getMatchingStacks());
        for(int i = 0; i < 3 && i < itemStacks.size(); ++i) {
            int p = i + SaucepanSlots.RESULT_1.ordinal();
            this.inventory.set(p, itemStacks.get(i).copy());
        }
    }

    public ItemStack getContainer(){
        return this.inventory.get(SaucepanSlots.CONTAINER.ordinal());
    }

    public ItemStack getWaterContainer(){
        return this.inventory.get(SaucepanSlots.WATER_CONTAINER.ordinal());
    }

    public void eraseWater(){
        if(this.inventory.get(SaucepanSlots.WATER_CONTAINER.ordinal()).isOf(Items.WATER_BUCKET)){
            this.inventory.set(SaucepanSlots.WATER_CONTAINER.ordinal(), new ItemStack(Items.BUCKET));
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public boolean compareList(List<ItemStack> itemStacks1, List<ItemStack> itemStacks2){
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

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(blockEntity instanceof SaucepanBlockEntity entity && !world.isClient){
            if(entity.cookingTime == 0){
                List<PotCookingRecipe> recipes = world.getRecipeManager().listAllOfType(CateRecipes.Pot_Cooking_RecipeType);
                for (PotCookingRecipe recipe : recipes) {
                    if (recipe.check(entity.getIngredient(), entity.getContainer())) {
                        entity.latestIngredient = recipe.ingredient;
                        entity.latestContainer = recipe.container.copy();
                        entity.latestResult = recipe.result;
                        entity.experience = recipe.experience;
                        entity.cookingTime = recipe.cookTime;
                        break;
                    }
                }
                entity.isCooking = false;
                return;
            }
            if(entity.getWaterContainer().isOf(Items.WATER_BUCKET) && entity.getContainer().isOf(entity.latestContainer.getItem()) &&
                    entity.getResults().get(0).isOf(Items.AIR) && entity.getResults().get(1).isOf(Items.AIR) && entity.getResults().get(2).isOf(Items.AIR) &&
                    entity.compareList(List.of(entity.latestIngredient.getMatchingStacks()), entity.getIngredient()) &&
                    (world.getBlockState(pos.down()).isOf(Blocks.CAMPFIRE) || world.getBlockState(pos.down()).isOf(Blocks.SOUL_CAMPFIRE)) &&
                    world.getBlockState(pos.down()).get(CampfireBlock.LIT)){
                if(entity.cookingTime == entity.cookingTick){
                    entity.insertResults(entity.latestResult);
                    entity.eraseWater();
                    entity.eraseIngredient();
                    return;
                }
                entity.isCooking = true;
                entity.cookingTick++;
            }else{
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
        return new SaucepanScreenHandler(syncId, inventory, this);
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
        if(this.world != null){
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
