package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.FabricImmortuosCalyx;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ConfigPacket {

    public static void send(ServerPlayer player, FriendlyByteBuf byteBuf){
        byteBuf.writeBoolean(FabricImmortuosCalyx.CONFIG.getOrDefault("ANTICHAT", true));
        byteBuf.writeBoolean(FabricImmortuosCalyx.CONFIG.getOrDefault("FORMATTEDINFECTCHAT", false));
        byteBuf.writeBoolean(FabricImmortuosCalyx.CONFIG.getOrDefault("INFECTEDCHATNOISE", true));
        byteBuf.writeInt(FabricImmortuosCalyx.CONFIG.getOrDefault("EFFECTCHAT", 40));
        ServerPlayNetworking.send(player, new ResourceLocation("immortuoscalyx:configupdate"), byteBuf);
    }
}
