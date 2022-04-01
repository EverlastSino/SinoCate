package com.everlastsino.cate.recipe.serializers;

import com.everlastsino.cate.recipe.recipes.MillingRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public record MillingRecipeSerializer<T extends MillingRecipe>(
        MillingRecipeSerializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

    public T read(Identifier identifier, JsonObject jsonObject) {
        JsonElement ingredientJsonElement = JsonHelper.hasArray(jsonObject, "ingredient") ?
                JsonHelper.getArray(jsonObject, "ingredient") : JsonHelper.getObject(jsonObject, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(ingredientJsonElement);
        String containerString = JsonHelper.getString(jsonObject, "container");
        ItemStack containerItemStack = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(containerString)).orElseThrow(
                () -> new IllegalStateException("Item: " + containerString + " does not exist")));
        JsonElement resultJsonElement = JsonHelper.hasArray(jsonObject, "result") ?
                JsonHelper.getArray(jsonObject, "result") : JsonHelper.getObject(jsonObject, "result");
        Ingredient results = Ingredient.fromJson(resultJsonElement);
        int cookingTime = Math.max(JsonHelper.getInt(jsonObject, "rounds", 1), 1);
        return this.recipeFactory.create(identifier, ingredient, containerItemStack, results, cookingTime);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
        ItemStack itemStack = packetByteBuf.readItemStack();
        Ingredient results = Ingredient.fromPacket(packetByteBuf);
        int i = packetByteBuf.readVarInt();
        return this.recipeFactory.create(identifier, ingredient, itemStack, results, i);
    }

    public void write(PacketByteBuf packetByteBuf, T millingRecipe) {
        millingRecipe.ingredient.write(packetByteBuf);
        packetByteBuf.writeItemStack(millingRecipe.container);
        millingRecipe.result.write(packetByteBuf);
        packetByteBuf.writeVarInt(millingRecipe.rounds);
    }

    public interface RecipeFactory<T extends MillingRecipe> {
        T create(Identifier id, Ingredient ingredient, ItemStack container, Ingredient result, int cookingTime);
    }
}

