package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SoundPacket {
    public static void encodeAndSend(FriendlyByteBuf buffer){
        ClientPlayNetworking.send(new ResourceLocation(Constants.MOD_ID, "soundpacket"), buffer);
    }
}
