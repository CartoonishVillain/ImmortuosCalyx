package com.cartoonishvillain.immortuoscalyx.data.player;

import com.cartoonishvillain.immortuoscalyx.data.ImmortuosPlayerData;

import java.io.Serializable;

public class NeoForgeInfectionPlayerData implements ImmortuosPlayerData, Serializable {
    //Todo: set up config for infectionTicks default
    int infectionPercent = 0;
    int infectionTicks = 110;

    @Override
    public int getInfectionPercent() {
        return infectionPercent;
    }

    @Override
    public void setInfectionPercent(int infectionPercent) {
        this.infectionPercent = infectionPercent;
    }

    @Override
    public boolean tickInfection() {
        boolean changedPercent = false;
        if (infectionPercent > 0) {
            infectionTicks--;
            if (infectionTicks <= 0) {
                infectionTicks = 150;
                if (infectionPercent < 100) {
                    infectionPercent++;
                    changedPercent = true;
                }
            }
        }
        return changedPercent;
    }

    @Override
    public int getTicks() {
        return infectionTicks;
    }

    @Override
    public void setTicks(int ticks) {
        infectionTicks = ticks;
    }
}
