package com.everlastsino.cate.recipe.recipes;

import com.everlastsino.cate.item.CateItems;
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

public class CuttingRecipe implements Recipe<Inventory> {
    public RecipeType<?> type;
    public Identifier id;
    public ItemStack ingredient;
    public ItemStack result;
    public ItemStack tool;
    public int damageTool;
    public int steps;

    public CuttingRecipe(Identifier id, ItemStack ingredient, ItemStack result, ItemStack tool, int damageTool, int steps) {
        this.type = CateRecipes.Cutting_RecipeType;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.tool = tool;
        this.damageTool = damageTool;
        this.steps = steps;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(CateItems.Cutting_Board);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CateRecipes.Cutting_RecipeSerializer;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return inventory.getStack(0).getItem().equals(this.ingredient.getItem());
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.ofSize(1);
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
