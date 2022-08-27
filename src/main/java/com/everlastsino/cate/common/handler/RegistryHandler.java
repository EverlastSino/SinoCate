package com.everlastsino.cate.common.handler;

import com.everlastsino.cate.Cate;
import com.everlastsino.cate.common.block.init.BlockInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {
    public static final DeferredRegister<Block> BLOCKS = handlerOf(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = handlerOf(ForgeRegistries.BLOCK_ENTITIES);
    public static final DeferredRegister<Item> ITEMS = handlerOf(ForgeRegistries.ITEMS);
    public static final DeferredRegister<MenuType<?>> MENUS = handlerOf(ForgeRegistries.CONTAINERS);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = handlerOf(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister<Feature<?>> FEATURES = handlerOf(ForgeRegistries.FEATURES);
    public static final DeferredRegister<SoundEvent> SOUNDS = handlerOf(ForgeRegistries.SOUND_EVENTS);

    public static void register() {
        final var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        BlockInit.init();
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> handlerOf(IForgeRegistry<T> reg) {
        return DeferredRegister.create(reg, Cate.MODID);
    }
}
