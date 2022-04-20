package com.everlastsino.cate.recipe.serializers;

import com.everlastsino.cate.recipe.recipes.SunDryingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public record SunDryingRecipeSerializer<T extends SunDryingRecipe>(
        SunDryingRecipeSerializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

    public T read(Identifier identifier, JsonObject jsonObject) {
        String ingredientString = JsonHelper.getString(jsonObject, "ingredient");
        ItemStack ingredient = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(ingredientString)).orElseThrow(
                () -> new IllegalStateException("Item: " + ingredientString + " does not exist")));
        String resultString = JsonHelper.getString(jsonObject, "result");
        ItemStack result = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(resultString)).orElseThrow(
                () -> new IllegalStateException("Item: " + resultString + " does not exist")));
        int dryingTime = JsonHelper.getInt(jsonObject, "dryingtime", 1000);
        return this.recipeFactory.create(identifier, ingredient, result, dryingTime);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        ItemStack ingredient = packetByteBuf.readItemStack();
        ItemStack result = packetByteBuf.readItemStack();
        int dryingTime = packetByteBuf.readVarInt();
        return this.recipeFactory.create(identifier, ingredient, result, dryingTime);
    }

    public void write(PacketByteBuf packetByteBuf, T recipe) {
        packetByteBuf.writeItemStack(recipe.ingredient);
        packetByteBuf.writeItemStack(recipe.result);
        packetByteBuf.writeVarInt(recipe.dryingTime);
    }

    public interface RecipeFactory<T extends SunDryingRecipe> {
        T create(Identifier id, ItemStack ingredient, ItemStack result, int dryingTime);
    }
}

