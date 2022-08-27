package com.everlastsino.cate.common.block.init;

import com.everlastsino.cate.common.handler.RegistryHandler;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.TreeMap;
import java.util.function.Supplier;

public class BlockInit {
    private static final TreeMap<String, BlockInfo> blockList = new TreeMap<>();

    public enum Reference {
        ;
        final String name;
        //Set it to null to avoid registering block item for it
        @Nullable
        final CreativeModeTab tab;
        final Supplier<Block> block;

        Reference(String name, Supplier<Block> block, @Nullable CreativeModeTab tab) {
            this.name = name;
            this.block = block;
            this.tab = tab;
        }

        Reference(String name, Supplier<Block> block) {
            this(name, block, CreativeModeTab.TAB_BUILDING_BLOCKS);
        }
    }

    public record BlockInfo(RegistryObject<Block> block, CreativeModeTab tab) {
    }

    public static void init() {
        final var references = Reference.values();
        for (final var ref : references)
            blockList.put(ref.name, new BlockInfo(RegistryHandler.BLOCKS.register(ref.name, ref.block), ref.tab));
        blockList.forEach((s, r) -> {
            if (r.tab == null) return;
            RegistryHandler.ITEMS.register(s, () -> new BlockItem(r.block().get(), new Item.Properties().tab(r.tab)));
        });
    }

    public static Block getRegisteredBlock(Reference ref) {
        return getRegistered(ref).orElse(Blocks.AIR);
    }

    public static RegistryObject<Block> getRegistered(Reference ref) {
        return blockList.get(ref.name).block;
    }
}
