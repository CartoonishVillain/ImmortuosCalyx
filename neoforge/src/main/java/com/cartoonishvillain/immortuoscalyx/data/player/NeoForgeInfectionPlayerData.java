package com.cartoonishvillain.immortuoscalyx.data.player;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.data.ImmortuosPlayerData;
import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;

import java.io.Serializable;
import java.util.ArrayList;

public class NeoForgeInfectionPlayerData implements ImmortuosPlayerData, Serializable {
    int infectionPercent = 0;
    int infectionTicks = CommonImmortuos.configData.getInfectionTicksPerPercentage();
    ArrayList<Symptom> symptoms = new ArrayList<>();

    @Override
    public int getInfectionPercent() {
        return infectionPercent;
    }

    @Override
    public void setInfectionPercent(int infectionPercent) {
        if (infectionPercent > 100) infectionPercent = 100;
        if (infectionPercent < 0) infectionPercent = 0;
        this.infectionPercent = infectionPercent;
    }

    @Override
    public boolean tickInfection() {
        boolean changedPercent = false;
        if (infectionPercent > 0) {
            infectionTicks--;
            if (infectionTicks <= 0) {
                infectionTicks = CommonImmortuos.configData.getInfectionTicksPerPercentage();;
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
    public ArrayList<Symptom> getSymptoms() {
        return symptoms;
    }

    @Override
    public void setSymptoms(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public void addSymptom(Symptom symptom) {
        symptoms.add(symptom);
    }

    @Override
    public void removeSymptom(Symptom symptom) {
        symptoms.remove(symptom);
    }
}
