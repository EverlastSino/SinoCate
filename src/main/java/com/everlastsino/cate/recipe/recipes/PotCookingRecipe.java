package com.everlastsino.cate.recipe.recipes;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.recipe.CateRecipes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class PotCookingRecipe implements Recipe<Inventory> {
    public final RecipeType<?> type;
    public final Identifier id;
    public final String group;
    public final Ingredient ingredient;
    public final ItemStack container;
    public final Ingredient result;
    public final float experience;
    public final int cookTime;

    public PotCookingRecipe(Identifier id, String group, Ingredient ingredient, ItemStack container, Ingredient result, float experience, int cookTime) {
        this.type = CateRecipes.Pot_Cooking_RecipeType;
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
        this.container = container;
        this.result = result;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(CateBlocks.Saucepan);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CateRecipes.Pot_Cooking_RecipeSerializer;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.ingredient.test(inventory.getStack(0));
    }

    public boolean check(List<ItemStack> itemStacks, ItemStack container){
        List<ItemStack> itemStack = List.of(this.ingredient.getMatchingStacks());
        int realSize = 0;
        for (ItemStack stack1 : itemStacks) {
            realSize += stack1.isOf(Items.AIR) ? 0 : 1;
        }
        if (realSize != itemStack.size()) return false;
        for (ItemStack stack : itemStack) {
            boolean has = false;
            for (ItemStack stack1 : itemStacks) {
                if (stack.getItem() == stack1.getItem()) {
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }
        return container.getItem() == this.container.getItem();
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
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.ingredient);
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
