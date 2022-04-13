package com.everlastsino.cate.util;

import com.everlastsino.cate.CateMain;
import com.everlastsino.cate.util.cookingExp.AllCookingExpStates;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CateListeners {

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register((world) -> {
            AllCookingExpStates.INSTANCE = world.getOverworld().getPersistentStateManager().get(
                    AllCookingExpStates::readFromNbt, "catecookingexps");
            if (AllCookingExpStates.INSTANCE == null) AllCookingExpStates.INSTANCE = new AllCookingExpStates();
            CateMain.CateLogger.info("Loaded SinoCate data.");
        });
        ServerLifecycleEvents.SERVER_STOPPING.register((world) -> {
            world.getOverworld().getChunkManager().getPersistentStateManager().set(
                    "catecookingexps", AllCookingExpStates.INSTANCE);
            CateMain.CateLogger.info("Saving SinoCate data.");
        });
    }

}