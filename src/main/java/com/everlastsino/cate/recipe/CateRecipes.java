package com.everlastsino.cate.recipe;

import com.everlastsino.cate.recipe.recipes.*;
import com.everlastsino.cate.recipe.serializers.*;
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

    public static RecipeType<SmokyGrillingRecipe> Smoky_Grilling_RecipeType;
    public static RecipeSerializer<SmokyGrillingRecipe> Smoky_Grilling_RecipeSerializer;

    public static void registerRecipes(){

        Pot_Cooking_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "pot_cooking"),
                new RecipeType<PotCookingRecipe>(){});
        Pot_Cooking_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "pot_cooking"),
                new PotCookingRecipeSerializer<>(PotCookingRecipe::new));

        Cutting_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "cutting"),
                new RecipeType<CuttingRecipe>(){});
        Cutting_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "cutting"),
                new CuttingRecipeSerializer<>(CuttingRecipe::new));

        Milling_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "milling"),
                new RecipeType<MillingRecipe>(){});
        Milling_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "milling"),
                new MillingRecipeSerializer<>(MillingRecipe::new));

        Steaming_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "steaming"),
                new RecipeType<SteamingRecipe>(){});
        Steaming_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "steaming"),
                new SteamingRecipeSerializer<>(SteamingRecipe::new));

        Smoky_Grilling_RecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("cate", "smoky_grilling"),
                new RecipeType<SmokyGrillingRecipe>(){});
        Smoky_Grilling_RecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("cate", "smoky_grilling"),
                new SmokyGrillingRecipeSerializer<>(SmokyGrillingRecipe::new));

    }

}
