package com.cartoonishvillain.immortuoscalyx.data.player;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.data.ImmortuosPlayerData;
import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import org.ladysnake.cca.api.v3.component.Component;

import java.util.ArrayList;
import java.util.Random;

public class PlayerInfectionComponent implements ImmortuosPlayerData, Component {
    private final Object provider;
    int infectionPercent = 0;
    int infectionTicks = CommonImmortuos.configData.getInfectionTicksPerPercentage();
    float resistance = 1f;
    ArrayList<Symptom> symptoms = new ArrayList<>();

    public PlayerInfectionComponent(Object provider){this.provider = provider;}
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
    public boolean tickInfection(MobEffectInstance mobEffectInstance) {
        boolean changedPercent = false;
        if (infectionPercent > 0) {
            infectionTicks--;
            if (infectionTicks <= 0) {
                infectionTicks = CommonImmortuos.configData.getInfectionTicksPerPercentage();
                if (infectionPercent < 100) {
                    if (mobEffectInstance != null) {
                        Random random = new Random();
                        if (mobEffectInstance.getAmplifier() >= random.nextInt(100)) {
                            //Do nothing, the gene has negated this increase
                        } else {
                            //Gene check failed
                            infectionPercent++;
                            changedPercent = true;
                        }
                    } else {
                        //Gene not installed to negate
                        infectionPercent++;
                        changedPercent = true;
                    }
                }
            }
        }

        if (resistance > 1f) resistance -= 0.000001f;
        if (resistance < 1f) resistance = 1f;
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

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        infectionTicks = tag.getInt("immortuosTicks");
        infectionPercent = tag.getInt("immortuosPercent");
        resistance = tag.getFloat("immortuosresist");
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putInt("immortuosTicks", infectionTicks);
        tag.putInt("immortuosPercent", infectionPercent);
        tag.putFloat("immortuosresist", resistance);
    }

    @Override
    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    @Override
    public float getResistance() {
        return resistance;
    }
}
