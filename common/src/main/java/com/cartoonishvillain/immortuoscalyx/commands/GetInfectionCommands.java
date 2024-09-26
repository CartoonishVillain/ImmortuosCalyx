package com.cartoonishvillain.immortuoscalyx.commands;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class GetInfectionCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("getInfectionPercent").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).executes(context -> {
                    return getInfectionStatus(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"));
                }))));
    }

    private static int getInfectionStatus(CommandSourceStack sourceStack, Collection<GameProfile> profiles) {
        for(GameProfile profile : profiles) {
            ServerPlayer player = sourceStack.getServer().getPlayerList().getPlayer(profile.getId());
            sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.getinfectionstatus", player.getName(), Services.PLATFORM.getInfectionPercentage(player)), true);
        }
        return 0;
    }
}
