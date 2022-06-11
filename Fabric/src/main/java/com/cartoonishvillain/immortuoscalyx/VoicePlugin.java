package com.cartoonishvillain.immortuoscalyx;
//
//import com.cartoonishvillain.immortuoscalyx.platform.Services;
//import de.maxhenkel.voicechat.api.VoicechatApi;
//import de.maxhenkel.voicechat.api.VoicechatPlugin;
//import de.maxhenkel.voicechat.api.events.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.world.entity.player.Player;
//
//public class VoicePlugin implements VoicechatPlugin {
//
//    @Override
//    public String getPluginId() {
//        return Constants.MOD_ID;
//    }
//
//    @Override
//    public void initialize(VoicechatApi api) {
//        VoicechatPlugin.super.initialize(api);
//    }
//
//    @Override
//    public void registerEvents(EventRegistration registration) {
//        VoicechatPlugin.super.registerEvents(registration);
//
//        registration.registerEvent(ClientReceiveSoundEvent.class, this::stopAudioIfInfected);
//    }
//
//    public void stopAudioIfInfected(ClientReceiveSoundEvent event) {
//        Player player = Minecraft.getInstance().level.getPlayerByUUID(event.getId());
//        if (Services.PLATFORM.getInfectionProgress(player) >= Services.PLATFORM.getEffectChat()) {
//            event.setRawAudio(null);
//        }
//
//    }
//}
