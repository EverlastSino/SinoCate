package com.everlastsino.cate.item;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.block.CateCrops;
import com.everlastsino.cate.itemGroup.CateItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class CateItems {
    //普通物品
    //农副产品
    public static final Item Paddy_Straw = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final Item Osmanthus = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //猎物
    public static final Item Oyster = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //调味品
    public static final Item Oyster_Sauce = new Item(new FabricItemSettings().group(CateItemGroups.CateGroup));

    //方块物品
    //普通方块
    public static final BlockItem Saucepan = new BlockItem(CateBlocks.Saucepan,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //草木
    public static final BlockItem Osmanthus_Tree_Sapling = new BlockItem(CateBlocks.Osmanthus_Tree_Sapling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Osmanthus_Tree_Leaves = new BlockItem(CateBlocks.Osmanthus_Tree_Leaves,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //作物
    //耕地
    public static final BlockItem Rough_Rice = new BlockItem(CateCrops.Paddy_Seedling,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Adzuki_Bean = new BlockItem(CateCrops.Adzuki_Bean,
            new FabricItemSettings().group(CateItemGroups.CateGroup));
    public static final BlockItem Ginger = new BlockItem(CateCrops.Ginger,
            new FabricItemSettings().group(CateItemGroups.CateGroup));

    //非耕地
    public static final BlockItem Grown_Paddy_Seedling = new BlockItem(CateCrops.Paddy,
            new FabricItemSettings().group(CateItemGroups.CateGroup));


    public static void registerItems(){
        //普通物品
        //农副产品
        Registry.register(Registry.ITEM, new Identifier("cate", "paddy_straw"), Paddy_Straw);
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus"), Osmanthus);

        //猎物
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster"), Oyster);

        //调味品
        Registry.register(Registry.ITEM, new Identifier("cate", "oyster_sauce"), Oyster_Sauce);

        //方块物品
        //普通方块
        Registry.register(Registry.ITEM, new Identifier("cate", "saucepan"), Saucepan);

        //草木
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus_tree_sapling"), Osmanthus_Tree_Sapling);
        Registry.register(Registry.ITEM, new Identifier("cate", "osmanthus_tree_leaves"), Osmanthus_Tree_Leaves);

        //作物
        //耕地
        Registry.register(Registry.ITEM, new Identifier("cate", "rough_rice"), Rough_Rice);
        Registry.register(Registry.ITEM, new Identifier("cate", "adzuki_bean"), Adzuki_Bean);
        Registry.register(Registry.ITEM, new Identifier("cate", "ginger"), Ginger);

        //非耕地
        Registry.register(Registry.ITEM, new Identifier("cate", "grown_paddy_seedling"), Grown_Paddy_Seedling);
    }

}
