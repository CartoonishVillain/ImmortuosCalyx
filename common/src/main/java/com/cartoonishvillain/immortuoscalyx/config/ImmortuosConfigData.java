package com.cartoonishvillain.immortuoscalyx.config;

import java.io.Serializable;
import java.util.List;

public class ImmortuosConfigData implements Serializable {
    String info;
    int infectionTicksPerPercentage;
    int infectionSymptomWarningMessage1;
    int infectionSymptomWarningMessage2;
    int infectionSymptomTemperatureSpeed;
    int infectionSymptomWaterBreathing;
    int infectionSymptomContagious;
    int infectionSymptomChatBlocked;
    int infectionSymptomTemperatureStrength;
    int infectionSymptomBlindness;
    int infectionSymptomConsumption;
    List<String> disabledGenes;
    List<String> disabledContamination;

    public ImmortuosConfigData(
            int infectionTicksPerPercentage,
            int infectionSymptomWarningMessage1,
            int infectionSymptomWarningMessage2,
            int infectionSymptomTemperatureSpeed,
            int infectionSymptomWaterBreathing,
            int infectionSymptomContagious,
            int infectionSymptomChatBlocked,
            int infectionSymptomTemperatureStrength,
            int infectionSymptomBlindness,
            int infectionSymptomConsumption,
            List<String> disabledGenes,
            List<String> disabledContamination
    ) {
        this.info = "For documentation on what each item does, see the readme file on github: https://github.com/CartoonishVillain/ImmortuosCalyx";
        this.infectionTicksPerPercentage = infectionTicksPerPercentage;
        this.infectionSymptomWarningMessage1 = infectionSymptomWarningMessage1;
        this.infectionSymptomWarningMessage2 = infectionSymptomWarningMessage2;
        this.infectionSymptomTemperatureSpeed = infectionSymptomTemperatureSpeed;
        this.infectionSymptomWaterBreathing = infectionSymptomWaterBreathing;
        this.infectionSymptomContagious = infectionSymptomContagious;
        this.infectionSymptomChatBlocked = infectionSymptomChatBlocked;
        this.infectionSymptomTemperatureStrength = infectionSymptomTemperatureStrength;
        this.infectionSymptomBlindness = infectionSymptomBlindness;
        this.infectionSymptomConsumption = infectionSymptomConsumption;
        this.disabledGenes = disabledGenes;
        this.disabledContamination = disabledContamination;
    }

    public static ImmortuosConfigData buildDefaultConfig() {
        return new ImmortuosConfigData(
                600, //infectionTicksPerPercentage
                10, //infectionSymptomWarningMessage1
                20, //infectionSymptomWarningMessage2
                30, //infectionSymptomTemperatureSpeed
                40, //infectionSymptomWaterBreathing
                50, //infectionSymptomContagious
                60, //infectionSymptomChatBlocked
                70, //infectionSymptomTemperatureStrength
                90, //infectionSymptomBlindness
                100, //infectionSymptomConsumption
                List.of(), //Disabled Genes
                List.of() //Disabled Contamination
        );
    }

    public int getInfectionTicksPerPercentage() {
        return infectionTicksPerPercentage;
    }

    public int getInfectionSymptomWarningMessage1() {
        return infectionSymptomWarningMessage1;
    }

    public int getInfectionSymptomWarningMessage2() {
        return infectionSymptomWarningMessage2;
    }

    public int getInfectionSymptomTemperatureSpeed() {
        return infectionSymptomTemperatureSpeed;
    }

    public int getInfectionSymptomWaterBreathing() {
        return infectionSymptomWaterBreathing;
    }

    public int getInfectionSymptomContagious() {
        return infectionSymptomContagious;
    }

    public int getInfectionSymptomChatBlocked() {
        return infectionSymptomChatBlocked;
    }

    public int getInfectionSymptomTemperatureStrength() {
        return infectionSymptomTemperatureStrength;
    }

    public int getInfectionSymptomBlindness() {
        return infectionSymptomBlindness;
    }

    public int getInfectionSymptomConsumption() {
        return infectionSymptomConsumption;
    }

    public List<String> getDisabledGenes() {
        return disabledGenes;
    }

    public List<String> getDisabledContamination() {
        return disabledContamination;
    }
}
