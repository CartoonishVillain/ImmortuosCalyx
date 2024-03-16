package com.cartoonishvillain.immortuoscalyx.commands;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class SetInfectionCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setInfectionPercent").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.argument("infectionPercent", IntegerArgumentType.integer()).executes(context -> {
                    return setInfected(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), IntegerArgumentType.getInteger(context, "infectionPercent"));
                })))));
    }


    private static int setInfected(CommandSourceStack sourceStack, Collection<GameProfile> profiles, int infectionPercent) {
        for (GameProfile gameProfile : profiles) {
            ServerPlayer serverPlayer = sourceStack.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if (serverPlayer != null) {
                Services.PLATFORM.setInfectionPercentage(serverPlayer, infectionPercent);
                sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.setpercentage", serverPlayer.getName(), infectionPercent), true);
            }
        }

        return 0;
    }
}
