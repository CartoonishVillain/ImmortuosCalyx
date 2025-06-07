package com.cartoonishvillain.immortuoscalyx.commands;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ImmortuosConfigCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("config").requires(cs -> cs.hasPermission(2))
                .then(Commands.literal("reload").executes(context ->
                        reloadConfig(context.getSource())
                ))));
    }

    private static int reloadConfig(CommandSourceStack sourceStack) {
        CommonImmortuos.loadConfig();

        for (ServerPlayer player : sourceStack.getServer().getPlayerList().getPlayers()) {
            Services.PLATFORM.sendConfigPacket(
                    Constants.encodeSCV(CommonImmortuos.getActiveGenes().keySet().stream().toList()),
                    Constants.encodeSCV(CommonImmortuos.getActiveContaminations().keySet().stream().toList()),
                    player
            );
        }

        sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.config.reload"), true);
        return 0;
    }
}
