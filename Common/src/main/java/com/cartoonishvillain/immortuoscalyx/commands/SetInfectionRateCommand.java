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

public class SetInfectionRateCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("setinfectionprogress")
                .requires(cs -> {return cs.hasPermission(2);})
                .then(Commands.argument("target", GameProfileArgument.gameProfile()).then(Commands.argument("progress", IntegerArgumentType.integer(0)).executes(context -> {
                    return setInfectionProgress(context.getSource(), GameProfileArgument.getGameProfiles(context, "target"), IntegerArgumentType.getInteger(context, "progress"));
                })))

        );
    }


    private static int setInfectionProgress(CommandSourceStack source, Collection<GameProfile> profiles, int amount){
        for(GameProfile gameProfile : profiles){
            ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if(serverPlayerEntity != null){
                Services.PLATFORM.setInfectionProgress(amount, serverPlayerEntity);
           }
        }
        source.sendSuccess(Component.translatable("progress.immortuoscalyx.set", amount), false);
        return 0;
    }
}
