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

    public static void registerGene(String gene, CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot1").then(Commands.literal(gene).executes(context -> {
                    return setGene1(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), gene);
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("GeneSlot2").then(Commands.literal(gene).executes(context -> {
                    return setGene2(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), gene);
                }))))));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("Quality").then(Commands.argument("quality", IntegerArgumentType.integer(0, 100)).executes(context -> {
                    return setQuality(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), IntegerArgumentType.getInteger(context, "quality"));
                }))))));

        registerGene("gene_immortuos", dispatcher);
        registerGene("gene_zombie", dispatcher);
        registerGene("gene_ocelot", dispatcher);
        registerGene("gene_turtle", dispatcher);
        registerGene("gene_iron_golem", dispatcher);
        registerGene("gene_frog", dispatcher);
        registerGene("gene_enderman", dispatcher);
        registerGene("gene_silverfish", dispatcher);
        registerGene("gene_vindicator", dispatcher);
        registerGene("gene_wither_skeleton", dispatcher);
        registerGene("gene_magma_cube", dispatcher);

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

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_giant").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_giant");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_glass").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_glass");
                }))))));

        dispatcher.register(Commands.literal("immortuoscalyx").then(Commands.literal("setGeneStatus").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile()).then(Commands.literal("ContaminationSlot").then(Commands.literal("contamination_shady").executes(context -> {
                    return setContamination(context.getSource(), GameProfileArgument.getGameProfiles(context, "player"), "contamination_shady");
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
