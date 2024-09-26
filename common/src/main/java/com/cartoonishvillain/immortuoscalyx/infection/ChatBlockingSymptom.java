package com.cartoonishvillain.immortuoscalyx.infection;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class ChatBlockingSymptom extends AbstractSymptom {

    @Override
    public void addSymptomEffect(ServerPlayer player) {
        player.addEffect(
                new MobEffectInstance(
                        Services.PLATFORM.INFECTION_CHAT(),
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
        player.removeEffect(Services.PLATFORM.INFECTION_CHAT());
    }

    public ChatBlockingSymptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.chatblock").withColor(9505804);
        symptom = Symptom.CHATBLOCK;
    }
}
