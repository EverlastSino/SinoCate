package com.everlastsino.cate.recipe.serializers;

import com.everlastsino.cate.recipe.recipes.CuttingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public record CuttingRecipeSerializer<T extends CuttingRecipe>(
        CuttingRecipeSerializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

    public T read(Identifier identifier, JsonObject jsonObject) {
        String ingredientString = JsonHelper.getString(jsonObject, "ingredient");
        ItemStack ingredient = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(ingredientString)).orElseThrow(
                () -> new IllegalStateException("Item: " + ingredientString + " does not exist")));
        String resultString = JsonHelper.getString(jsonObject, "result");
        int resultAmount = JsonHelper.getInt(jsonObject, "resultAmount", 1);
        ItemStack result = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(resultString)).orElseThrow(
                () -> new IllegalStateException("Item: " + resultString + " does not exist")), resultAmount);
        String toolString = JsonHelper.getString(jsonObject, "tool");
        ItemStack tool = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(toolString)).orElseThrow(
                () -> new IllegalStateException("Item: " + toolString + " does not exist")));
        int damageTool = JsonHelper.getInt(jsonObject, "damageTool", 0);
        int steps = JsonHelper.getInt(jsonObject, "steps", 1);
        return this.recipeFactory.create(identifier, ingredient, result, tool, damageTool, steps);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        ItemStack ingredient = packetByteBuf.readItemStack();
        ItemStack result = packetByteBuf.readItemStack();
        ItemStack tool = packetByteBuf.readItemStack();
        int damageTool = packetByteBuf.readVarInt();
        int steps = packetByteBuf.readVarInt();
        return this.recipeFactory.create(identifier, ingredient, result, tool, damageTool, steps);
    }

    public void write(PacketByteBuf packetByteBuf, T cuttingRecipe) {
        packetByteBuf.writeItemStack(cuttingRecipe.ingredient);
        packetByteBuf.writeItemStack(cuttingRecipe.result);
        packetByteBuf.writeItemStack(cuttingRecipe.tool);
        packetByteBuf.writeVarInt(cuttingRecipe.damageTool);
        packetByteBuf.writeVarInt(cuttingRecipe.steps);
    }

    public interface RecipeFactory<T extends CuttingRecipe> {
        T create(Identifier id, ItemStack ingredient, ItemStack result, ItemStack tool, int damageTool, int steps);
    }
}

