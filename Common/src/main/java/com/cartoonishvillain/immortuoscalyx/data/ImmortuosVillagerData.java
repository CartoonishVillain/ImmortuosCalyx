package com.cartoonishvillain.immortuoscalyx.data;

/**
 * For villagers to determine their cultist status
 * Should be low odds for a cultist to spawn in
 * Cultists have a low chance to infect users when traded with
 */
public interface ImmortuosVillagerData {
    /*
     * In implementations add boolean for isCultist
     */

    /**
     * @return is the villager a cultist
     */
    boolean getIsCultist();

    /**
     * Set if a given villager is a cultist
     */
    void setIsCultist(boolean isCultist);
}
