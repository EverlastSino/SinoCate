package com.everlastsino.cate.util;

import com.everlastsino.cate.util.commands.CookingExperienceCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class CateCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> CookingExperienceCommand.register(dispatcher));
    }

}
