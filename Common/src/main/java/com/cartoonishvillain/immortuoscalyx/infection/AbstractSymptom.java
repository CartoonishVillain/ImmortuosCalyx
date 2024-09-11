package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public abstract class AbstractSymptom {
    protected Component symptomAlert;
    protected Symptom symptom;

    public void chirpAndAddSymptomEffect(ServerPlayer player) {
        addSymptomEffect(player);
        chirpEffect(player);
    }

    public void addSymptomEffect(ServerPlayer player) {

    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void chirpEffect(ServerPlayer player) {
        player.sendSystemMessage(symptomAlert);
    }

    public abstract void removeSymptomEffect(ServerPlayer player);

    protected abstract void tickedSymptomEffect(ServerPlayer player);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof AbstractSymptom) {
            AbstractSymptom that = (AbstractSymptom) o;
            return symptom == that.symptom;
        } else if (o instanceof Symptom) { return symptom == o; }
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symptom);
    }
}
