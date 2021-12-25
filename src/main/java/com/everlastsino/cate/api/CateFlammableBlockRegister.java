package com.everlastsino.cate.api;

import com.everlastsino.cate.block.CateBlocks;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class CateFlammableBlockRegister {
    public static void register(){
        FlammableBlockRegistry.getDefaultInstance().add(CateBlocks.Osmanthus_Tree_Leaves, 100, 80);
    }
}
