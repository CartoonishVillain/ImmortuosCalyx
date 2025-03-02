package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ImmortuosChatMixin {
    @Inject(at = @At("HEAD"), method = "broadcastChatMessage", cancellable = true)
    public void immortuosChat(PlayerChatMessage pMessage, CallbackInfo ci) {
        ServerPlayer player = ((ServerGamePacketListenerImpl) (Object) this).getPlayer();
        if (player != null && player.hasEffect(Services.PLATFORM.INFECTION_CHAT())) {
            player.level().playSound(null, player.getOnPos().above(1), Services.PLATFORM.HUMANOID_AMBIENT(), SoundSource.PLAYERS);
            ci.cancel();
        }
    }
}
