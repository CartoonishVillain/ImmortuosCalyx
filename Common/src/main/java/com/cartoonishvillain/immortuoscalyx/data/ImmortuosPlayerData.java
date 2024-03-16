package com.cartoonishvillain.immortuoscalyx.data;

/**
 * The data needed to track player infections
 */
public interface ImmortuosPlayerData {
    /*
     * In implementations add integers for both infectionPercentage and infectionTicks
     */

    /**
     * @return Infection percent
     */
    int getInfectionPercent();

    /**
     * Set the infection percent.
     */
    void setInfectionPercent(int infectionPercent);

    /**
     * If infectionPercent is greater than 0, reduce infectionTicks.
     * When infection ticks is less than or equal to 0, reset infection ticks to config value and increment infectionPercent
     * @return true if percentage changes.
     */
    boolean tickInfection();

    int getTicks();
    void setTicks(int ticks);
}
