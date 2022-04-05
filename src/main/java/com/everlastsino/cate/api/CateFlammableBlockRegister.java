package com.everlastsino.cate.api;

import com.everlastsino.cate.block.CateBlocks;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class CateFlammableBlockRegister {
    public static void register(){
        FlammableBlockRegistry.getDefaultInstance().add(CateBlocks.Osmanthus_Tree_Leaves, 100, 80);
        FlammableBlockRegistry.getDefaultInstance().add(CateBlocks.Wooden_Sieve, 160, 70);
        FlammableBlockRegistry.getDefaultInstance().add(CateBlocks.Osmanthus_Tree_Log, 160, 70);
        FlammableBlockRegistry.getDefaultInstance().add(CateBlocks.Wooden_Steamer, 200, 40);
        FlammableBlockRegistry.getDefaultInstance().add(CateBlocks.Smoking_Rack, 600, 40);
    }
}
