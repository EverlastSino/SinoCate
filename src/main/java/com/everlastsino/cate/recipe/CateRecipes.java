package com.everlastsino.cate.recipe;

import com.everlastsino.cate.recipe.recipes.CuttingRecipe;
import com.everlastsino.cate.recipe.recipes.MillingRecipe;
import com.everlastsino.cate.recipe.recipes.PotCookingRecipe;
import com.everlastsino.cate.recipe.recipes.SteamingRecipe;
import com.everlastsino.cate.recipe.serializers.CuttingRecipeSerializer;
import com.everlastsino.cate.recipe.serializers.MillingRecipeSerializer;
import com.everlastsino.cate.recipe.serializers.PotCookingRecipeSerializer;
import com.everlastsino.cate.recipe.serializers.SteamingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateRecipes {
    public static RecipeType<PotCookingRecipe> Pot_Cooking_RecipeType;
    public static RecipeSerializer<PotCookingRecipe> Pot_Cooking_RecipeSerializer;

    public static RecipeType<CuttingRecipe> Cutting_RecipeType;
    public static RecipeSerializer<CuttingRecipe> Cutting_RecipeSerializer;

    public static RecipeType<MillingRecipe> Milling_RecipeType;
    public static RecipeSerializer<MillingRecipe> Milling_RecipeSerializer;

    public static RecipeType<SteamingRecipe> Steaming_RecipeType;
    public static RecipeSerializer<SteamingRecipe> Steaming_RecipeSerializer;

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

        Cutting_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "cutting"),
                new RecipeType<CuttingRecipe>() {
                    @Override
                    public String toString() {
                        return "cutting";
                    }
                });
        Cutting_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "cutting"),
                new CuttingRecipeSerializer<>(CuttingRecipe::new));

        Milling_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "milling"),
                new RecipeType<MillingRecipe>() {
                    @Override
                    public String toString() {
                        return "milling";
                    }
                });
        Milling_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "milling"),
                new MillingRecipeSerializer<>(MillingRecipe::new));

        Steaming_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "steaming"),
                new RecipeType<SteamingRecipe>() {
                    @Override
                    public String toString() {
                        return "steaming";
                    }
                });
        Steaming_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "steaming"),
                new SteamingRecipeSerializer<>(SteamingRecipe::new));

    }

}
