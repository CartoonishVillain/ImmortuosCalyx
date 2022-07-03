package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.ValidPlayer;

@ForgeVoicechatPlugin
public class VoicePlugin implements VoicechatPlugin {
    public static VoicechatApi voicechatApi;

    @Override
    public String getPluginId() {
        return Constants.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        voicechatApi = api;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::stopAudioIfInfected);
    }

    public void stopAudioIfInfected(MicrophonePacketEvent event) {
        ServerPlayer serverPlayer;

        if(event.getSenderConnection() != null && event.getSenderConnection().getPlayer() != null) {
            UUID uuid  = event.getSenderConnection().getPlayer().getUuid();
            if (ForgeImmortuosCalyx.serverInstance.getPlayerList().getPlayer(uuid) != null) {
                serverPlayer = ForgeImmortuosCalyx.serverInstance.getPlayerList().getPlayer(uuid);
                if (Services.PLATFORM.getInfectionProgress(serverPlayer) >= Services.PLATFORM.getEffectChat() &&
                        ValidPlayer(serverPlayer) && Services.PLATFORM.getVoiceChatModSupport()) {
                    event.cancel();
                }
            }
        }
    }
}
