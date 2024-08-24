package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ContagionSymptom extends AbstractSymptom {

    @Override
    protected void removeSymptomEffect(ServerPlayer player) {

    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //Symptom is not handled here.
        //Mixin into players attacks. Only run odds for melee attacks.
    }

    public ContagionSymptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.contagious");
        symptom = Symptom.CONTAGION;
    }
}
