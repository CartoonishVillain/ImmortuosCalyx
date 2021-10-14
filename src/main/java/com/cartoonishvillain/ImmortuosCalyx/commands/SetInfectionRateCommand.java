package com.cartoonishvillain.ImmortuosCalyx.commands;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

public class SetInfectionRateCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("setinfectionprogress")
                .requires(cs -> {return cs.hasPermission(2);})
                .then(Commands.argument("target", GameProfileArgument.gameProfile()).then(Commands.argument("progress", IntegerArgumentType.integer(0)).executes(context -> {
                    return setInfectionProgress(context.getSource(), GameProfileArgument.getGameProfiles(context, "target"), IntegerArgumentType.getInteger(context, "progress"));
                })))

        );
    }


    private static int setInfectionProgress(CommandSource source, Collection<GameProfile> profiles, int amount){
        for(GameProfile gameProfile : profiles){
            ServerPlayerEntity serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            serverPlayerEntity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                h.setInfectionProgress(amount);
            });
        }
        source.sendSuccess(new TranslationTextComponent("progress.immortuoscalyx.set", amount), false);
        return 0;
    }
}
