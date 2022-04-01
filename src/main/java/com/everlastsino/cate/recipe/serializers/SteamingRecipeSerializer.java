package com.everlastsino.cate.recipe.serializers;

import com.everlastsino.cate.recipe.recipes.SteamingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public record SteamingRecipeSerializer<T extends SteamingRecipe>(
        SteamingRecipeSerializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

    public T read(Identifier identifier, JsonObject jsonObject) {
        String ingredientString = JsonHelper.getString(jsonObject, "ingredient");
        ItemStack ingredient = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(ingredientString)).orElseThrow(
                () -> new IllegalStateException("Item: " + ingredientString + " does not exist")));
        String resultString = JsonHelper.getString(jsonObject, "result");
        ItemStack result = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(resultString)).orElseThrow(
                () -> new IllegalStateException("Item: " + resultString + " does not exist")));
        int cookingTime = JsonHelper.getInt(jsonObject, "cookingtime", 100);
        return this.recipeFactory.create(identifier, ingredient, result, cookingTime);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        ItemStack ingredient = packetByteBuf.readItemStack();
        ItemStack result = packetByteBuf.readItemStack();
        int i = packetByteBuf.readVarInt();
        return this.recipeFactory.create(identifier, ingredient, result, i);
    }

    public void write(PacketByteBuf packetByteBuf, T steamingRecipe) {
        packetByteBuf.writeItemStack(steamingRecipe.ingredient);
        packetByteBuf.writeItemStack(steamingRecipe.result);
        packetByteBuf.writeVarInt(steamingRecipe.cookTime);
    }

    public interface RecipeFactory<T extends SteamingRecipe> {
        T create(Identifier id, ItemStack ingredient, ItemStack result, int cookingTime);
    }
}

