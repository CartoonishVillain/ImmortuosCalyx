package com.cartoonishvillain.immortuoscalyx.data.player;

import com.cartoonishvillain.immortuoscalyx.data.ImmortuosPlayerData;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.CompoundTag;

public class PlayerInfectionComponent implements ImmortuosPlayerData, Component {
    //Todo: set up config for infectionTicks default
    private final Object provider;
    int infectionPercent = 0;
    int infectionTicks = 110;

    public PlayerInfectionComponent(Object provider){this.provider = provider;}
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
                infectionTicks = 110;
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

    @Override
    public void readFromNbt(CompoundTag tag) {
        infectionTicks = tag.getInt("immortuosTicks");
        infectionPercent = tag.getInt("immortuosPercent");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("immortuosTicks", infectionTicks);
        tag.putInt("immortuosPercent", infectionPercent);
    }
}
