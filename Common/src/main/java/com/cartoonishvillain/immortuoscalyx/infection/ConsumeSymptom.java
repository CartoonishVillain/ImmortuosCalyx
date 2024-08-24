package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ConsumeSymptom extends AbstractSymptom {

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //Symptom is not handled here.
        //Mixin into players attacks. Only run odds for melee attacks.
    }

    public ConsumeSymptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.consumption");
        symptom = Symptom.CONSUME;
    }
}
