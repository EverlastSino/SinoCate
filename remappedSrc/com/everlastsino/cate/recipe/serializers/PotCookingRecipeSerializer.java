package com.everlastsino.cate.recipe.serializers;

import com.everlastsino.cate.recipe.recipes.PotCookingRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public record PotCookingRecipeSerializer<T extends PotCookingRecipe>(
        com.everlastsino.cate.recipe.serializers.PotCookingRecipeSerializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

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
        float experience = JsonHelper.getFloat(jsonObject, "experience", 0.0F);
        int cookingTime = JsonHelper.getInt(jsonObject, "cookingtime", 100);
        return this.recipeFactory.create(identifier, ingredient, containerItemStack, results, experience, cookingTime);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
        ItemStack itemStack = packetByteBuf.readItemStack();
        Ingredient results = Ingredient.fromPacket(packetByteBuf);
        float f = packetByteBuf.readFloat();
        int i = packetByteBuf.readVarInt();
        return this.recipeFactory.create(identifier, ingredient, itemStack, results, f, i);
    }

    public void write(PacketByteBuf packetByteBuf, T potCookingRecipe) {
        potCookingRecipe.ingredient.write(packetByteBuf);
        packetByteBuf.writeItemStack(potCookingRecipe.container);
        potCookingRecipe.result.write(packetByteBuf);
        packetByteBuf.writeFloat(potCookingRecipe.experience);
        packetByteBuf.writeVarInt(potCookingRecipe.cookTime);
    }

    public interface RecipeFactory<T extends PotCookingRecipe> {
        T create(Identifier id, Ingredient ingredient, ItemStack container, Ingredient result, float experience, int cookingTime);
    }
}

