package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class WarningSymptom1 extends AbstractSymptom {

    @Override
    public void removeSymptomEffect(ServerPlayer player) {

    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //No effect
    }

    public WarningSymptom1() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.warning1");
        symptom = Symptom.WARNING1;
    }
}
