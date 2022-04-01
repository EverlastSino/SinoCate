package com.everlastsino.cate.recipe.recipes;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.recipe.CateRecipes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SteamingRecipe implements Recipe<Inventory> {
    public RecipeType<?> type;
    public Identifier id;
    public ItemStack ingredient;
    public ItemStack result;
    public int cookTime;

    public SteamingRecipe(Identifier id, ItemStack ingredient, ItemStack result, int cookTime) {
        this.type = CateRecipes.Steaming_RecipeType;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.cookTime = cookTime;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(CateBlocks.Wooden_Steamer);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CateRecipes.Steaming_RecipeSerializer;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return inventory.getStack(0).isOf(this.ingredient.getItem());
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.result;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(Ingredient.ofStacks(this.ingredient));
        return defaultedList;
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }
}
