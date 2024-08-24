package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ChatBlockingSymptom extends AbstractSymptom {

    @Override
    protected void tickedSymptomEffect(ServerPlayer player) {
        //Symptom is not handled here.
        //Mixin into chat to block chat.
        //Also block voice chat where available.
    }

    public ChatBlockingSymptom() {
        symptomAlert = Component.translatable("immortuoscalyx.symptom.chatblock");
        symptom = Symptom.CHATBLOCK;
    }
}
