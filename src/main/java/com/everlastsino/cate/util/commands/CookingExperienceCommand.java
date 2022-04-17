package com.everlastsino.cate.util.commands;

import com.everlastsino.cate.util.cookingExp.AllCookingExpStates;
import com.everlastsino.cate.util.cookingExp.CookingExperienceState;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CookingExperienceCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("cookingexp")
                .then(
                        argument("player", EntityArgumentType.player()).requires((source) -> source.hasPermissionLevel(2))
                                .executes(CookingExperienceCommand::executeQueryOthersExp)
                        .then(argument("exp", FloatArgumentType.floatArg(0, 1e7f)).executes(CookingExperienceCommand::executeAddExp))
                        .then(literal("clear").executes(CookingExperienceCommand::executeClearExp)))
                .executes(CookingExperienceCommand::executeQueryExp)
        );
    }

    private static int executeClearExp(CommandContext<ServerCommandSource> context) {
        try {
            PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            AllCookingExpStates.INSTANCE.clear(player);
            return 0;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int executeAddExp(CommandContext<ServerCommandSource> context) {
        try {
            PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            float exp = FloatArgumentType.getFloat(context, "exp");
            AllCookingExpStates.INSTANCE.add(player, exp);
            return 0;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int executeQueryExp(CommandContext<ServerCommandSource> context) {
        try {
            PlayerEntity player = context.getSource().getPlayer();
            CookingExperienceState state = AllCookingExpStates.INSTANCE.get(player);
            player.sendMessage(Text.of("You are now on level " + state.level + " with exp " + state.experience), false);
            return 0;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int executeQueryOthersExp(CommandContext<ServerCommandSource> context) {
        try {
            PlayerEntity player = context.getSource().getPlayer(), target = EntityArgumentType.getPlayer(context, "player");
            CookingExperienceState state = AllCookingExpStates.INSTANCE.get(target);
            player.sendMessage(Text.of("You are now on level " + state.level + " with exp " + state.experience), false);
            return 0;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
