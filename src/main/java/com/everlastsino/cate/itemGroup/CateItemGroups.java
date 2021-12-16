package com.everlastsino.cate.itemGroup;

import com.everlastsino.cate.block.CateCrops;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CateItemGroups {

    //默认物品组
    public static final ItemGroup CateGroup = FabricItemGroupBuilder.build(
            new Identifier("cate", "cate"),
            () -> new ItemStack(CateCrops.Paddy)
    );

}
