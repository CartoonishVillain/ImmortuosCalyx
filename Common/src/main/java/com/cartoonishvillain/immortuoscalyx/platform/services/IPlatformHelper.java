package com.cartoonishvillain.immortuoscalyx.platform.services;

import com.cartoonishvillain.immortuoscalyx.client.BlindnessFog;
import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }


    /**
     * Sets the value of the infection of a given user
     * @param serverPlayer - The player to set the infection percentage for.
     * @param infectionPercentage - The percentage to set the infection to. (up to 100%, down to 0%)
     */
    void setInfectionPercentage(ServerPlayer serverPlayer, int infectionPercentage);

    /**
     * Sets the value of the infection of a given user
     * @param serverPlayer - The player to return the infection percentage from.
     * @return - The infection percentage of the player.
     */
    int getInfectionPercentage(ServerPlayer serverPlayer);

    /**
     * Gets the symptom list of a given user
     * @param serverPlayer - The player to get symptoms from.
     * @return - The list of active symptoms of the player
     */
    ArrayList<Symptom> getSymptoms(ServerPlayer serverPlayer);

    /**
     * Adds a symptom to the list of a given user
     * @param serverPlayer - The player to add a symptom to.
     * @param  symptom - The symptom to add.
     */
    void addSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom);

    /**
     * removes a symptom from the list of a given user
     * @param serverPlayer - The player to remove a symptom symptom.
     * @param  symptom - The symptom to remove.
     */
    void removeSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom);

    /**
     * Sets the symptom list of a given user
     * @param player - The player to set symptoms to
     * @param symptoms - The list of symptoms to add to the player
     */
    void setSymptoms(ServerPlayer player, ArrayList<Symptom> symptoms);

    /**
     * Ticks the infection timer of a given player, if applicable
     * @param serverPlayer - The player to tick
     * @return - Did the tick succeed?
     */
    boolean tickInfection(ServerPlayer serverPlayer);

    /**
     * CALL ON THE CLIENT ONLY
     * Adds the Infected Blindness to the MOB_EFFECT_FOG list.
     */
    default void clientUpdate() {
        FogRenderer.MOB_EFFECT_FOG.add(new BlindnessFog());
        int x = 0;
    }

    /**
     * MOB EFFECTS
     */
    Holder<MobEffect> INFECTION_BLIND();
    Holder<MobEffect> INFECTION_WATER_BREATHING();
    Holder<MobEffect> INFECTION_STRENGTH_TEMPERATURE();
    Holder<MobEffect> INFECTION_STRENGTH();
    Holder<MobEffect> INFECTION_RESIST();
    Holder<MobEffect> INFECTION_WEAKEN();
    Holder<MobEffect> INFECTION_VULNERABLE();
    Holder<MobEffect> INFECTION_SPEED_TEMPERATURE();
    Holder<MobEffect> INFECTION_SPEED();
    Holder<MobEffect> INFECTION_SLOW();
    Holder<MobEffect> INFECTION_CHAT();
    Holder<MobEffect> INFECTION_CONTAGION();
    Holder<MobEffect> INFECTION_CONSUMPTION();

    /**
     * Sound Effects
     */
    SoundEvent HUMANOID_AMBIENT();
    SoundEvent HUMANOID_HURT();
    SoundEvent HUMANOID_DEATH();
    SoundEvent INJECT();
    SoundEvent EXTRACT();
    SoundEvent SCAN_BAD();
    SoundEvent SCAN_GOOD();
}