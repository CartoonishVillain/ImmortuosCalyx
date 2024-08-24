package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class Temperature2Symptom extends AbstractSymptom {

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //Symptom is not handled here.
        //Mixin into players attacks. Only run odds for melee attacks.
    }

    public Temperature2Symptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.temperature2");
        symptom = Symptom.TEMP2;
    }
}
