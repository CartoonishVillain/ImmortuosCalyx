package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public abstract class AbstractSymptom {
    protected Component symptomAlert;
    protected Symptom symptom;

    protected void addSymptomEffect(ServerPlayer player) {
        player.sendSystemMessage(symptomAlert);
    }

    protected abstract void removeSymptomEffect(ServerPlayer player);

    protected abstract void tickedSymptomEffect(ServerPlayer player);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSymptom that = (AbstractSymptom) o;
        return symptom == that.symptom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symptom);
    }
}
