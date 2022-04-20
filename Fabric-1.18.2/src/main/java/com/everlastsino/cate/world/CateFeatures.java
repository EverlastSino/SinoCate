package com.everlastsino.cate.world;

import com.everlastsino.cate.world.features.CateOreFeatures;
import com.everlastsino.cate.world.features.CateTreeFeatures;

public class CateFeatures {

    public static void registerFeatures() {
        CateOreFeatures.register();
        CateTreeFeatures.register();
    }

}
