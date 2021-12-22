package com.everlastsino.cate.screen;

import com.everlastsino.cate.screen.screens.SaucepanScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CateScreenHandlers {
    public static ScreenHandlerType<SaucepanScreenHandler> Saucepan_ScreenHandler;

    public static void registerScreenHandlers(){
        Saucepan_ScreenHandler = ScreenHandlerRegistry.registerExtended(new Identifier("cate", "saucepan_screen_handler"), SaucepanScreenHandler::new);
    }
}
