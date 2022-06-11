package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.ClientImmortuosInitalizer;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import com.cartoonishvillain.immortuoscalyx.networking.ObfuscatedChatPacket;
import com.cartoonishvillain.immortuoscalyx.networking.SoundPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;
import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.ValidPlayer;

@Mixin(LocalPlayer.class)
public class AntiChatMixin {

    @Inject(at = @At("HEAD"), method = "sendChat", cancellable = true)
    private void Immortuoschat(MessageSigner signer, String string, Component component, CallbackInfo ci){
        Player player = ((LocalPlayer) (Object) this);
        if(player != null && ValidPlayer(player)) {
            InfectionComponent h = INFECTION.get(player);
            String name = player.getName().getString();
            String format = "<" + name + "> ";
            if(component == null) {
                if (!(string.charAt(0) == '/')) {

                    if ((h.getInfectionProgress() >= ClientImmortuosInitalizer.chatEffectMark && ClientImmortuosInitalizer.chatScreamingEnabled))
                        SoundPacket.encodeAndSend(new FriendlyByteBuf(Unpooled.buffer()));

                    if (h.getInfectionProgress() >= ClientImmortuosInitalizer.chatEffectMark && ClientImmortuosInitalizer.chatDisabledEnabled && ClientImmortuosInitalizer.chatScrambledEnabled) {
//                playerList.broadcastMessage((new TextComponent(format + ChatFormatting.OBFUSCATED + component)), ChatType.CHAT, uUID);
                        ObfuscatedChatPacket.encodeAndSend(format, string, new FriendlyByteBuf(Unpooled.buffer()));
                        ci.cancel();
                    }
                    if (h.getInfectionProgress() >= ClientImmortuosInitalizer.chatEffectMark && ClientImmortuosInitalizer.chatDisabledEnabled && !ClientImmortuosInitalizer.chatScrambledEnabled) {
                        ci.cancel();
                    }
                    ;//if the player's infection is @ or above 40%, they can no longer speak in text chat.

                }
            } else {
                //Todo: Component code? If found to be needed. Right now doesn't seem to be.
            }
        }
    }

}
