package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.effects.ImmortuosEffect;
import com.cartoonishvillain.immortuoscalyx.infection.*;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;

public class AbstractInfectionHandler {
    /**
     * Handles the tick mechanics of the infection. Each platform is commanded to run the tick.
     * tickInfection returns true if the tick updates the infection percentage. When it does, we want to update symptoms
     * with an active update.
     * @param player - Player to tick infection on.
     */
    public static void playerTick(ServerPlayer player) {
        if (Services.PLATFORM.tickInfection(player)) activeAdditiveSymptomUpdate(player, Services.PLATFORM.getSymptoms(player));
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
        mobEffectInstance.getEffect().value() != Services.PLATFORM.INFECTION_BLIND().value() &&
        mobEffectInstance.getEffect().value() != Services.PLATFORM.INFECTION_CONSUMPTION().value()) return true;
        else return false;
    }
}
