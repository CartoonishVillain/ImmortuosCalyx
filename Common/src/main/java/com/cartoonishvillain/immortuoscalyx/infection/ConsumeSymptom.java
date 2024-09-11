package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ConsumeSymptom extends AbstractSymptom {

    //TODO Make the consumption damage effect, after damage types are added.
    @Override
    public void removeSymptomEffect(ServerPlayer player) {

    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {

    }

    public ConsumeSymptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.consumption");
        symptom = Symptom.CONSUME;
    }
}
