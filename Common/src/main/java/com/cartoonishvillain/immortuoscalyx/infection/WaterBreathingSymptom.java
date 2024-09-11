package com.cartoonishvillain.immortuoscalyx.infection;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class WaterBreathingSymptom extends AbstractSymptom {

    @Override
    public void addSymptomEffect(ServerPlayer player) {
        player.addEffect(
                new MobEffectInstance(
                        Services.PLATFORM.INFECTION_WATER_BREATHING(),
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
        player.removeEffect(Services.PLATFORM.INFECTION_WATER_BREATHING());
    }

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //Symptom is not handled here.
        //Mixin into players attacks. Only run odds for melee attacks.
    }

    public WaterBreathingSymptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.waterbreathing");
        symptom = Symptom.WATERBREATHING;
    }
}
