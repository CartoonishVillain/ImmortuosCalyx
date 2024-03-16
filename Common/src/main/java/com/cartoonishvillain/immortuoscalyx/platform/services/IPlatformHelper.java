package com.cartoonishvillain.immortuoscalyx.platform.services;

import net.minecraft.server.level.ServerPlayer;

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
     *
     * @param serverPlayer
     * @return
     */
    boolean tickInfection(ServerPlayer serverPlayer);
}