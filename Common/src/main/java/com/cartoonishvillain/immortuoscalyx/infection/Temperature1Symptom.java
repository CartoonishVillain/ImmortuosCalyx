package com.cartoonishvillain.immortuoscalyx.infection;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class Temperature1Symptom extends AbstractSymptom {

    @Override
    public void addSymptomEffect(ServerPlayer player) {
        player.addEffect(
                new MobEffectInstance(
                        Services.PLATFORM.INFECTION_SPEED_TEMPERATURE(),
                        MobEffectInstance.INFINITE_DURATION,
                        1,
                        true,
                        false,
                        false

                )
        );
    }

    @Override
    public void removeSymptomEffect(ServerPlayer player) {
        player.removeEffect(Services.PLATFORM.INFECTION_SPEED_TEMPERATURE());
        player.removeEffect(Services.PLATFORM.INFECTION_SPEED());
        player.removeEffect(Services.PLATFORM.INFECTION_SLOW());
    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {

    }

    public Temperature1Symptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.temperature1");
        symptom = Symptom.TEMP1;
    }
}
