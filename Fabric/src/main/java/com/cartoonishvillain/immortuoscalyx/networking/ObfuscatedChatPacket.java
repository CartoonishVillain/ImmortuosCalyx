package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.Charset;

public class ObfuscatedChatPacket {
    public static void encodeAndSend(String name, String message, FriendlyByteBuf buffer){
        buffer.writeInt(name.length());
        buffer.writeCharSequence(name, Charset.defaultCharset());
        buffer.writeInt(message.length());
        buffer.writeCharSequence(message, Charset.defaultCharset());
        ClientPlayNetworking.send(new ResourceLocation(Constants.MOD_ID, "chatpacket"), buffer);
    }
}
