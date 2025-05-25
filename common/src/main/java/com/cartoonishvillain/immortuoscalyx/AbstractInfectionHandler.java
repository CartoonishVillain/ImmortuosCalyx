package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.damage.ImmortuosDamageTypes;
import com.cartoonishvillain.immortuoscalyx.effects.ImmortuosEffect;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.infection.*;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class AbstractInfectionHandler {
    /**
     * Handles the tick mechanics of the infection. Each platform is commanded to run the tick.
     * tickInfection returns true if the tick updates the infection percentage. When it does, we want to update symptoms
     * with an active update.
     * @param player - Player to tick infection on.
     */
    public static void playerTick(ServerPlayer player) {
        if (playerAffected(player)) {
            if (Services.PLATFORM.tickInfection(player))
                activeAdditiveSymptomUpdate(player, Services.PLATFORM.getSymptoms(player));
            //every second and a half, re-enforce effects, incase they were lost
            if (player.tickCount % 30 == 0) {
                ArrayList<Symptom> symptoms = Services.PLATFORM.getSymptoms(player);

                if (symptoms.contains(Symptom.WATERBREATHING)) {
                    new WaterBreathingSymptom().addSymptomEffect(player);
                }

                if (symptoms.contains(Symptom.TEMP1)) {
                    new Temperature1Symptom().addSymptomEffect(player);
                }

                if (symptoms.contains(Symptom.CONTAGION)) {
                    new ContagionSymptom().addSymptomEffect(player);
                }

                if (symptoms.contains(Symptom.CHATBLOCK)) {
                    new ChatBlockingSymptom().addSymptomEffect(player);
                }

                if (symptoms.contains(Symptom.TEMP2)) {
                    new Temperature2Symptom().addSymptomEffect(player);
                }

                if (symptoms.contains(Symptom.BLIND)) {
                    new BlindnessSymptom().addSymptomEffect(player);
                }

                if (symptoms.contains(Symptom.CONSUME)) {
                    new ConsumeSymptom().addSymptomEffect(player);
                }
            }
        } else {
            if (player.tickCount % 30 == 0) { //Users in creative or spectator don't tick or have effects.
                player.removeEffect(Services.PLATFORM.INFECTION_BLIND());
                player.removeEffect(Services.PLATFORM.INFECTION_CONTAGION());
                player.removeEffect(Services.PLATFORM.INFECTION_CONSUMPTION());
                player.removeEffect(Services.PLATFORM.INFECTION_CHAT());
                player.removeEffect(Services.PLATFORM.INFECTION_WATER_BREATHING());
                player.removeEffect(Services.PLATFORM.INFECTION_WEAKEN());
                player.removeEffect(Services.PLATFORM.INFECTION_VULNERABLE());
                player.removeEffect(Services.PLATFORM.INFECTION_STRENGTH());
                player.removeEffect(Services.PLATFORM.INFECTION_STRENGTH_TEMPERATURE());
                player.removeEffect(Services.PLATFORM.INFECTION_SPEED());
                player.removeEffect(Services.PLATFORM.INFECTION_SLOW());
                player.removeEffect(Services.PLATFORM.INFECTION_SPEED_TEMPERATURE());
                player.removeEffect(Services.PLATFORM.INFECTION_RESIST());
            }
        }
    }

    /**
     * The active symptom update is used for additive symptom updates that occur in the process of the game.
     * The difference between this and an "inactive" update, is that active updates will alert players about symptoms being added.
     * Inactive updates would occur say, during world join, where that'd just be potentially a lot of messages.
     * This method should also only run for additive cases, such as when ticking infection, or as a part of the command process.
     * @param player - Player to update symptoms of.
     * @param symptoms - The list of symptoms.
     */
    public static void activeAdditiveSymptomUpdate(ServerPlayer player, ArrayList<Symptom> symptoms) {
        int percentage = Services.PLATFORM.getInfectionPercentage(player);

        // check if the symptom exists before checking the percentage
        if (!symptoms.contains(Symptom.WARNING1) && percentage >= CommonImmortuos.configData.getInfectionSymptomWarningMessage1()) {
            //if the symptom isn't present, and we have a percentage greater than or equal to the config value, add the symptom
            activeAddSymptom(player, new WarningSymptom1());
        }

        if (!symptoms.contains(Symptom.WARNING2) && percentage >= CommonImmortuos.configData.getInfectionSymptomWarningMessage2()) {
            activeAddSymptom(player, new WarningSymptom2());
        }

        if (!symptoms.contains(Symptom.WATERBREATHING) && percentage >= CommonImmortuos.configData.getInfectionSymptomWaterBreathing()) {
            activeAddSymptom(player, new WaterBreathingSymptom());
        }

        if (!symptoms.contains(Symptom.TEMP1) && percentage >= CommonImmortuos.configData.getInfectionSymptomTemperatureSpeed()) {
            activeAddSymptom(player, new Temperature1Symptom());
        }

        if (!symptoms.contains(Symptom.CONTAGION) && percentage >= CommonImmortuos.configData.getInfectionSymptomContagious()) {
            activeAddSymptom(player, new ContagionSymptom());
        }

        if (!symptoms.contains(Symptom.CHATBLOCK) && percentage >= CommonImmortuos.configData.getInfectionSymptomChatBlocked()) {
            activeAddSymptom(player, new ChatBlockingSymptom());
        }

        if (!symptoms.contains(Symptom.TEMP2) && percentage >= CommonImmortuos.configData.getInfectionSymptomTemperatureStrength()) {
            activeAddSymptom(player, new Temperature2Symptom());
        }

        if (!symptoms.contains(Symptom.BLIND) && percentage >= CommonImmortuos.configData.getInfectionSymptomBlindness()) {
            activeAddSymptom(player, new BlindnessSymptom());
        }

        if (!symptoms.contains(Symptom.CONSUME) && percentage >= CommonImmortuos.configData.getInfectionSymptomConsumption()) {
            activeAddSymptom(player, new ConsumeSymptom());
        }
    }

    /**
     * Adds the symptom to the provided symptoms list, and renders the effect of the symptom to the player
     * @param player the player who is gaining a symptom
     * @param newSymptom the symptom the player is gaining
     */
    private static void activeAddSymptom(ServerPlayer player, AbstractSymptom newSymptom) {
        Services.PLATFORM.addSymptom(player, newSymptom);
    }

    /**
     * The active symptom update is used for subtractive symptom updates that occur in the process of the game.
     * The difference between this and an "inactive" update, is that active updates will alert players about symptoms being added.
     * Inactive updates would occur say, during world join, where that'd just be potentially a lot of messages.
     * This method should also only run for subtractive cases, such as when curing players, or as a part of the command process.
     * @param player - Player to update symptoms of.
     * @param symptoms - The list of symptoms.
     */
    public static void activeSubtractiveSymptomUpdate(ServerPlayer player, ArrayList<Symptom> symptoms) {
        int percentage = Services.PLATFORM.getInfectionPercentage(player);

        // check if the symptom exists before checking the percentage
        if (symptoms.contains(Symptom.WARNING1) && percentage < CommonImmortuos.configData.getInfectionSymptomWarningMessage1()) {
            //if the symptom isn't present, and we have a percentage greater than or equal to the config value, add the symptom
            removeSymptom(player, new WarningSymptom1());
        }

        if (symptoms.contains(Symptom.WARNING2) && percentage < CommonImmortuos.configData.getInfectionSymptomWarningMessage2()) {
            removeSymptom(player, new WarningSymptom2());
        }

        if (symptoms.contains(Symptom.WATERBREATHING) && percentage < CommonImmortuos.configData.getInfectionSymptomWaterBreathing()) {
            removeSymptom(player, new WaterBreathingSymptom());
        }

        if (symptoms.contains(Symptom.TEMP1) && percentage < CommonImmortuos.configData.getInfectionSymptomTemperatureSpeed()) {
            removeSymptom(player, new Temperature1Symptom());
        }

        if (symptoms.contains(Symptom.CONTAGION) && percentage < CommonImmortuos.configData.getInfectionSymptomContagious()) {
            removeSymptom(player, new ContagionSymptom());
        }

        if (symptoms.contains(Symptom.CHATBLOCK) && percentage < CommonImmortuos.configData.getInfectionSymptomChatBlocked()) {
            removeSymptom(player, new ChatBlockingSymptom());
        }

        if (symptoms.contains(Symptom.TEMP2) && percentage < CommonImmortuos.configData.getInfectionSymptomTemperatureStrength()) {
            removeSymptom(player, new Temperature2Symptom());
        }

        if (symptoms.contains(Symptom.BLIND) && percentage < CommonImmortuos.configData.getInfectionSymptomBlindness()) {
            removeSymptom(player, new BlindnessSymptom());
        }

        if (symptoms.contains(Symptom.CONSUME) && percentage < CommonImmortuos.configData.getInfectionSymptomConsumption()) {
            removeSymptom(player, new ConsumeSymptom());
        }
    }

    /**
     * Removes the symptom from the provided symptoms list, and renders the effect of the removal to the player
     * @param player the player who is losing a symptom
     * @param removedSymptom the symptom the player is losing
     * */
    private static void removeSymptom(ServerPlayer player, AbstractSymptom removedSymptom) {
        Services.PLATFORM.removeSymptom(player, removedSymptom);
    }

    /**
     * Runs when the infection percentage set command is used. Fires additive and subtractive symptom updates to ensure that symptoms set properly.
     * @param player - the player who was updated.
     */
    public static void commandSymptomUpdate(ServerPlayer player) {
        ArrayList<Symptom> symptoms = Services.PLATFORM.getSymptoms(player);
        activeAdditiveSymptomUpdate(player, symptoms);
        activeSubtractiveSymptomUpdate(player, symptoms);
    }

    /**
     * The inactive symptom update is used for additive symptom updates that occur to sync data.
     * The difference between this and an "active" update, is that active updates will alert players about symptoms being added.
     * This method should also only run for resync cases, such as keeping track of the symptom list when a player joins in/
     * @param player - Player to update symptoms of.
     */
    public static void inactiveAdditiveSymptomUpdate(ServerPlayer player) {
        int percentage = Services.PLATFORM.getInfectionPercentage(player);
        ArrayList<Symptom> symptoms = new ArrayList<>();

        // check if the symptom exists before checking the percentage
        if (percentage >= CommonImmortuos.configData.getInfectionSymptomWarningMessage1()) {
            //if the symptom isn't present, and we have a percentage greater than or equal to the config value, add the symptom
            symptoms.add(Symptom.WARNING1);
        }

        if (percentage >= CommonImmortuos.configData.getInfectionSymptomWarningMessage2()) {
            symptoms.add(Symptom.WARNING2);
        }

        if (percentage >= CommonImmortuos.configData.getInfectionSymptomWaterBreathing()) {
            symptoms.add(Symptom.WATERBREATHING);
        }

        if (percentage >= CommonImmortuos.configData.getInfectionSymptomTemperatureSpeed()) {
            symptoms.add(Symptom.TEMP1);
        }

        if (percentage >= CommonImmortuos.configData.getInfectionSymptomContagious()) {
            symptoms.add(Symptom.CONTAGION);
        }

        if (percentage >= CommonImmortuos.configData.getInfectionSymptomChatBlocked()) {
            symptoms.add(Symptom.CHATBLOCK);
        }

        if (percentage >= CommonImmortuos.configData.getInfectionSymptomTemperatureStrength()) {
            symptoms.add(Symptom.TEMP2);
        }

        if (!symptoms.contains(Symptom.BLIND) && percentage >= CommonImmortuos.configData.getInfectionSymptomBlindness()) {
            symptoms.add(Symptom.BLIND);
        }

        if (!symptoms.contains(Symptom.CONSUME) && percentage >= CommonImmortuos.configData.getInfectionSymptomConsumption()) {
            symptoms.add(Symptom.CONSUME);
        }

        Services.PLATFORM.setSymptoms(player, symptoms);
    }

    /**
     * Used to hide potion effects from the client if they don't need to see them
     * @param mobEffectInstance - the instance to check against
     * @return true, if the potion effect should be hidden
     */
    public static boolean isHiddenImmortuosEffect(MobEffectInstance mobEffectInstance) {
        //We want to hide all symptom effects, except for blindness. If we hide blindness, blindness doesn't work.
        if (mobEffectInstance.getEffect().value() instanceof ImmortuosEffect &&
        mobEffectInstance.getEffect().value() != Services.PLATFORM.GENE_IRON_GOLEM_ACTIVE().value() &&
        mobEffectInstance.getEffect().value() != Services.PLATFORM.INFECTION_BLIND().value()) return true;
        else return false;
    }

    public static void checkForHarvest(LivingEntity entity, DamageSource source) {
        if ((source.getEntity() instanceof Player || source.getDirectEntity() instanceof Player) && !entity.level().isClientSide) {
            Player player;
            if (source.getEntity() instanceof  Player) player = (Player) source.getEntity();
            else player = (Player) source.getDirectEntity();

            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Services.PLATFORM.GENE_RIPPER() ||
            player.getItemInHand(InteractionHand.OFF_HAND).getItem() == Services.PLATFORM.GENE_RIPPER()) {
                if (player.getRandom().nextInt(100) < 10) {
                    ItemEntity itemEntity = new ItemEntity(player.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Services.PLATFORM.UNIDENTIFIED_GENE()));
                    itemEntity.setPos(entity.getX(), entity.getY(), entity.getZ());
                    entity.level().addFreshEntity(itemEntity);
                }
            }
        }
    }

    public static void convertPlayer(ServerPlayer serverPlayer, DamageSource damageSource) {
        //If the infection consumed the player, spawn a mob
        boolean shouldSpawnInfected = damageSource.type().msgId().equals("infection_damage");

        //If the infection didn't kill the player, but they were still heavily infected, chance to spawn a mob
        if (!shouldSpawnInfected) {
            int chance = -1;
            int infectionPercentage = Services.PLATFORM.getInfectionPercentage(serverPlayer);

            if (infectionPercentage == 100) shouldSpawnInfected = true; //if the player was 100% infected, spawn the entity anyway
            else if (infectionPercentage >= 95) chance = 90; // for every 5% less infection, reduce odds of infected spawning by 10%, down to 75% infection
            else if (infectionPercentage >= 90) chance = 80;
            else if (infectionPercentage >= 85) chance = 70;
            else if (infectionPercentage >= 80) chance = 60;
            else if (infectionPercentage >= 75) chance = 50;

            if (chance != -1) {
                int roll = serverPlayer.getRandom().nextInt(100);
                if (roll < infectionPercentage) shouldSpawnInfected = true; // If the roll is below the percentage threshold, set the spawn to true
            }
        }

        if (shouldSpawnInfected) {
            Level world = serverPlayer.level();
            if (!world.isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) world;
                infectedEntitySummoner(serverPlayer, serverLevel);
            }
        }
    }

    private static void infectedEntitySummoner(ServerPlayer entity, ServerLevel serverLevel) {
        InfectedHumanEntity infectedHumanEntity = new InfectedHumanEntity(Services.PLATFORM.getInfectedHuman(), serverLevel);
        infectedHumanEntity.setPUsername(entity.getScoreboardName());
        infectedHumanEntity.setCustomName(entity.getName());
        infectedHumanEntity.setCustomNameVisible(true);
        infectedHumanEntity.setPersistenceRequired();
        infectedHumanEntity.setPUUID(entity.getUUID());
        infectedHumanEntity.setPos(entity.getX(), entity.getY() + 0.1, entity.getZ());
        serverLevel.addFreshEntity(infectedHumanEntity);
    }

    public static void infectionCheck(ServerPlayer target, int infectionChance) {
        float armorResistance = target.getArmorValue() * 1.5f; //Each armor value reduces infection chance by 2%
        float infectionResistance = Services.PLATFORM.getResistance(target);
        float finalInfectionRate = (infectionChance/infectionResistance) - armorResistance;
        if (finalInfectionRate < 1) finalInfectionRate = 1; //finalInfectionRate is minimum 1.
        if (target.getRandom().nextInt(100) <= finalInfectionRate) { //if our random roll is less than or equal to the infection rate, we infect the target player.
            Services.PLATFORM.setInfectionPercentage(target, 1);
            target.level().playSound(null, target.getOnPos().above(1), Services.PLATFORM.HUMANOID_HURT(), SoundSource.PLAYERS);
        }
    }

    public static void useImmortuosSample(ServerPlayer pTarget) {
        if (Services.PLATFORM.getInfectionPercentage(pTarget) < 1) { // If the player isn't infected
            Services.PLATFORM.setInfectionPercentage(pTarget, 1); // Infect them.
            commandSymptomUpdate(pTarget);
        }
    }

    public static void foodEat(ItemStack stack, ServerPlayer player) {
        if (stack.getItem() == Services.PLATFORM.IMMORTUOS_EGG()) {
            AbstractInfectionHandler.useImmortuosSample(player);
            commandSymptomUpdate(player);
        }
    }

    public static void useAntiParasitic(LivingEntity pTarget) {
        pTarget.hurt(
                new DamageSource(pTarget.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(ImmortuosDamageTypes.organ_damage)
                ), 3
        );

        if (pTarget instanceof ServerPlayer) { // If a player is not contagious, anti parasitic has a mild curing effect
            if (Services.PLATFORM.getInfectionPercentage((ServerPlayer) pTarget) < CommonImmortuos.configData.getInfectionSymptomContagious()) {
                Services.PLATFORM.setInfectionPercentage(
                        (ServerPlayer) pTarget,
                        Services.PLATFORM.getInfectionPercentage((ServerPlayer) pTarget) - 15
                );
                commandSymptomUpdate((ServerPlayer) pTarget);
            }

            Services.PLATFORM.setResistance((ServerPlayer) pTarget, 2.5f);
        }
    }

    public static void useCalyxanide(LivingEntity pTarget) {
        if (pTarget instanceof ServerPlayer) {
            if (Services.PLATFORM.getInfectionPercentage((ServerPlayer) pTarget) > 70) pTarget.hurt( //If infection is above 70%, harm the player for curing significantly
                    new DamageSource(pTarget.level().registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(ImmortuosDamageTypes.organ_damage)
                    ), 10);

            //Subtract 60 from the infection percentage
            Services.PLATFORM.setInfectionPercentage(
                    (ServerPlayer) pTarget,
                    Services.PLATFORM.getInfectionPercentage((ServerPlayer) pTarget) - 60
            );
            commandSymptomUpdate((ServerPlayer) pTarget);
        }

        if (pTarget instanceof InfectedEntity) {
            pTarget.hurt( //Infected entities are killed by calyxanide
                    new DamageSource(pTarget.level().registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(ImmortuosDamageTypes.organ_damage)
                    ), 20);
        }
    }

    public static void foreignHealthCheck(LivingEntity target, ServerPlayer viewer) {
        viewer.sendSystemMessage(Component.literal("===(" + target.getScoreboardName() + ")===").withStyle(ChatFormatting.GREEN));
        viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.health", target.getHealth()));
        if (target instanceof ServerPlayer) {
            viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.food", ((ServerPlayer) target).getFoodData().getFoodLevel()));
            viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.infection", Services.PLATFORM.getInfectionPercentage((ServerPlayer) target) + "%"));
            viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.resistance", Services.PLATFORM.getResistance((ServerPlayer) target)));
        }
        if (target instanceof InfectedEntity) {
            viewer.sendSystemMessage(Component.translatable("scanner.immotuoscalyx.infected_entity").withStyle(ChatFormatting.RED));
        }
    }

    public static void selfHealthCheck(ServerPlayer viewer) {
        viewer.sendSystemMessage(Component.literal("===(" + viewer.getScoreboardName() + ")===").withStyle(ChatFormatting.GREEN));
        viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.health", viewer.getHealth()));
        if (viewer instanceof ServerPlayer) {
            viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.food", ((ServerPlayer) viewer).getFoodData().getFoodLevel()));
            viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.infection", Services.PLATFORM.getInfectionPercentage((ServerPlayer) viewer) + "%"));
            viewer.sendSystemMessage(Component.translatable("scanner.immortuoscalyx.resistance", Services.PLATFORM.getResistance((ServerPlayer) viewer)));
        }
    }

    public static boolean playerAffected(ServerPlayer player) {
        return !(player.isCreative() || player.isSpectator());
    }
}
