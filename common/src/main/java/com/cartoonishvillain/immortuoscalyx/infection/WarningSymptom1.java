package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;

public class WarningSymptom1 extends AbstractSymptom {
    public WarningSymptom1() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.warning1").withColor(9505804);
        symptom = Symptom.WARNING1;
    }
}
