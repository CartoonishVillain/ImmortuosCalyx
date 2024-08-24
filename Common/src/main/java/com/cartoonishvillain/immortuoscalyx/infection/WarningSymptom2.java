package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class WarningSymptom2 extends AbstractSymptom {

    @Override
    protected void removeSymptomEffect(ServerPlayer player) {

    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //No Effect
    }

    public WarningSymptom2() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.warning2");
        symptom = Symptom.WARNING2;
    }
}
