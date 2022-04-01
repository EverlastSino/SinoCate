package com.everlastsino.cate.api;

import com.everlastsino.cate.block.CateBlocks;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class CateFuelItemRegister {
    public static void register(){
        FuelRegistry.INSTANCE.add(CateBlocks.Osmanthus_Tree_Sapling, 100);
        FuelRegistry.INSTANCE.add(CateBlocks.Osmanthus_Tree_Leaves, 300);
        FuelRegistry.INSTANCE.add(CateBlocks.Osmanthus_Tree_Log, 800);
        FuelRegistry.INSTANCE.add(CateBlocks.Wooden_Sieve, 600);
    }
}
