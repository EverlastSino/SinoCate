package com.everlastsino.cate.screen;

import com.everlastsino.cate.screen.screens.SaucepanScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class CateScreens {

    public static void registerScreens(){
        //gui注册
        ScreenRegistry.register(CateScreenHandlers.Saucepan_ScreenHandler, SaucepanScreen::new);
    }

}
