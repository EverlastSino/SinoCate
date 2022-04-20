package com.everlastsino.cate.api;

import com.everlastsino.cate.item.CateItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;

public class CateCompostableItemRegister {

    private static void addCompostable(Item item, float chance) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }

    public static void register() {
        addCompostable(CateItems.Orange_Tree_Leaves, 0.3f);
        addCompostable(CateItems.Osmanthus_Tree_Leaves, 0.3f);
        addCompostable(CateItems.Paddy_Straw, 0.5f);
        addCompostable(CateItems.Osmanthus, 0.5f);
        addCompostable(CateItems.Orange, 0.5f);
        addCompostable(CateItems.Green_Soybean, 0.5f);
        addCompostable(CateItems.Soybean, 0.5f);
        addCompostable(CateItems.Adzuki_Bean, 0.5f);
        addCompostable(CateItems.Rough_Rice, 0.5f);
        addCompostable(CateItems.Ginger, 0.5f);
    }

}
