package com.everlastsino.cate.itemGroup;

import com.everlastsino.cate.item.CateItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CateItemGroups {

    public static final ItemGroup Flour_Products = FabricItemGroupBuilder.build(
            new Identifier("cate", "flour_products"),
            () -> new ItemStack(CateItems.Dough)
    );

    public static final ItemGroup Cooker = FabricItemGroupBuilder.build(
            new Identifier("cate", "cooker"),
            () -> new ItemStack(CateItems.Hearth)
    );

    public static final ItemGroup Plant = FabricItemGroupBuilder.build(
            new Identifier("cate", "plant"),
            () -> new ItemStack(CateItems.Paddy_Straw)
    );

    public static final ItemGroup Dishes = FabricItemGroupBuilder.build(
            new Identifier("cate", "dishes"),
            () -> new ItemStack(CateItems.Smoked_Porkchop)
    );

}
