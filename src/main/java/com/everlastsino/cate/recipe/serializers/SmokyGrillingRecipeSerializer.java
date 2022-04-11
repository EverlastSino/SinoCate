package com.everlastsino.cate.recipe.serializers;

import com.everlastsino.cate.recipe.recipes.SmokyGrillingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public record SmokyGrillingRecipeSerializer<T extends SmokyGrillingRecipe>(
        SmokyGrillingRecipeSerializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

    public T read(Identifier identifier, JsonObject jsonObject) {
        String ingredientString = JsonHelper.getString(jsonObject, "ingredient");
        ItemStack ingredient = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(ingredientString)).orElseThrow(
                () -> new IllegalStateException("Item: " + ingredientString + " does not exist")));
        String resultString = JsonHelper.getString(jsonObject, "result");
        ItemStack result = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(resultString)).orElseThrow(
                () -> new IllegalStateException("Item: " + resultString + " does not exist")));
        int cookingTime = JsonHelper.getInt(jsonObject, "cookingtime", 100);
        String moreSmokeString = JsonHelper.getString(jsonObject, "moresmoke");
        ItemStack moreSmoke = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(moreSmokeString)).orElseThrow(
                () -> new IllegalStateException("Item: " + moreSmokeString + " does not exist")));
        return this.recipeFactory.create(identifier, ingredient, result, cookingTime, moreSmoke);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        ItemStack ingredient = packetByteBuf.readItemStack();
        ItemStack result = packetByteBuf.readItemStack();
        int i = packetByteBuf.readVarInt();
        ItemStack moreSmoke = packetByteBuf.readItemStack();
        return this.recipeFactory.create(identifier, ingredient, result, i, moreSmoke);
    }

    public void write(PacketByteBuf packetByteBuf, T recipe) {
        packetByteBuf.writeItemStack(recipe.ingredient);
        packetByteBuf.writeItemStack(recipe.result);
        packetByteBuf.writeVarInt(recipe.cookTime);
        packetByteBuf.writeItemStack(recipe.moreSmoke);
    }

    public interface RecipeFactory<T extends SmokyGrillingRecipe> {
        T create(Identifier id, ItemStack ingredient, ItemStack result, int cookingTime, ItemStack moreSmoke);
    }
}

