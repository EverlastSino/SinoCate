package com.everlastsino.cate.recipe;

import com.everlastsino.cate.recipe.recipes.PotCookingRecipe;
import com.everlastsino.cate.recipe.serializers.PotCookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateRecipes {
    public static RecipeType<PotCookingRecipe> Pot_Cooking_RecipeType;
    public static RecipeSerializer<PotCookingRecipe> Pot_Cooking_RecipeSerializer;

    public static void registerRecipes(){
        Pot_Cooking_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "pot_cooking"),
                new RecipeType<PotCookingRecipe>() {
                    @Override
                    public String toString() {
                        return "pot_cooking";
                    }
                });
        Pot_Cooking_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "pot_cooking"),
                new PotCookingRecipeSerializer<>(PotCookingRecipe::new));
    }
}
