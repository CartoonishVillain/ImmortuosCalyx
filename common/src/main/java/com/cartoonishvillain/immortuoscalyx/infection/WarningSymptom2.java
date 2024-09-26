package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class WarningSymptom2 extends AbstractSymptom {
    public WarningSymptom2() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.warning2").withColor(9505804);
        symptom = Symptom.WARNING2;
    }
}
