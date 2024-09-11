package com.cartoonishvillain.immortuoscalyx.infection;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class Temperature2Symptom extends AbstractSymptom {

    @Override
    public void addSymptomEffect(ServerPlayer player) {
        player.addEffect(
                new MobEffectInstance(
                        Services.PLATFORM.INFECTION_STRENGTH_TEMPERATURE(),
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
        player.removeEffect(Services.PLATFORM.INFECTION_STRENGTH_TEMPERATURE());
        player.removeEffect(Services.PLATFORM.INFECTION_STRENGTH());
        player.removeEffect(Services.PLATFORM.INFECTION_WEAKEN());
        player.removeEffect(Services.PLATFORM.INFECTION_RESIST());
        player.removeEffect(Services.PLATFORM.INFECTION_VULNERABLE());
    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {

    }

    public Temperature2Symptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.temperature2");
        symptom = Symptom.TEMP2;
    }
}
