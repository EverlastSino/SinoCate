package com.everlastsino.cate.item;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateItemTags {
    public static TagKey<Item> Flour_Products;

    public static void register() {
        Flour_Products = register("flour_products");
    }

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier("cate", id));
    }

}
