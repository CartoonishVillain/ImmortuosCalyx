package com.cartoonishvillain.immortuoscalyx.commands;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
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

public class SetGeneCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("Quality").then(Commands.argument("quality", IntegerArgumentType.integer(0, 100)).executes(context -> {
                    return setQuality(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), IntegerArgumentType.getInteger(context, "quality"));
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal("gene_immortuos").executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_immortuos");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal("gene_zombie").executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_zombie");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal("gene_ocelot").executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_ocelot");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal("gene_turtle").executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_turtle");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal("gene_iron_golem").executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_iron_golem");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal("gene_frog").executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_frog");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal("gene_immortuos").executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_immortuos");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal("gene_zombie").executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_zombie");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal("gene_ocelot").executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_ocelot");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal("gene_turtle").executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_turtle");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal("gene_iron_golem").executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_iron_golem");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal("gene_frog").executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "gene_frog");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_hydrophobia").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_hydrophobia");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_genetic_destablization").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_genetic_destablization");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_stagger").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_stagger");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_knee_pastafication").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_knee_pastafication");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_heliophobia").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_heliophobia");
                }))))));
    }


    private static int setGene1(CommandSourceStack sourceStack, Collection<GameProfile> profiles, String gene) {
        for (GameProfile gameProfile : profiles) {
            ServerPlayer serverPlayer = sourceStack.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if (serverPlayer != null) {
                Services.PLATFORM.editGeneSlot(serverPlayer, "slot1", gene);
                AbstractInfectionHandler.commandSymptomUpdate(serverPlayer);
                sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.setgene", "GeneSlot1", serverPlayer.getName(), gene), true);
            }
        }

        return 0;
    }

    private static int setGene2(CommandSourceStack sourceStack, Collection<GameProfile> profiles, String gene) {
        for (GameProfile gameProfile : profiles) {
            ServerPlayer serverPlayer = sourceStack.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if (serverPlayer != null) {
                Services.PLATFORM.editGeneSlot(serverPlayer, "slot2", gene);
                AbstractInfectionHandler.commandSymptomUpdate(serverPlayer);
                sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.setgene", "GeneSlot2", serverPlayer.getName(), gene), true);
            }
        }

        return 0;
    }

    private static int setContamination(CommandSourceStack sourceStack, Collection<GameProfile> profiles, String gene) {
        for (GameProfile gameProfile : profiles) {
            ServerPlayer serverPlayer = sourceStack.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if (serverPlayer != null) {
                Services.PLATFORM.editGeneSlot(serverPlayer, "contamination", gene);
                AbstractInfectionHandler.commandSymptomUpdate(serverPlayer);
                sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.setgene", "ContaminationSlot", serverPlayer.getName(), gene), true);
            }
        }

        return 0;
    }

    private static int setQuality(CommandSourceStack sourceStack, Collection<GameProfile> profiles, int quality) {
        for (GameProfile gameProfile : profiles) {
            ServerPlayer serverPlayer = sourceStack.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if (serverPlayer != null) {
                Services.PLATFORM.setGeneQuality(serverPlayer, quality);
                AbstractInfectionHandler.commandSymptomUpdate(serverPlayer);
                sourceStack.sendSuccess(() -> Component.translatable("immortuoscalyx.command.return.setquality", serverPlayer.getName(), quality), true);
            }
        }

        return 0;
    }
}
