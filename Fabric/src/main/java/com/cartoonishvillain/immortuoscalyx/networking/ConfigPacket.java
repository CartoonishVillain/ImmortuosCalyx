package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.FabricImmortuosCalyx;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ConfigPacket {

    public static void send(ServerPlayer player, FriendlyByteBuf byteBuf){
        byteBuf.writeBoolean(FabricImmortuosCalyx.config.playerToggles.ANTICHAT);
        byteBuf.writeBoolean(FabricImmortuosCalyx.config.otherDetails.FORMATTEDINFECTCHAT);
        byteBuf.writeBoolean(FabricImmortuosCalyx.config.playerToggles.INFECTEDCHATNOISE);
        byteBuf.writeInt(FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT);
        ServerPlayNetworking.send(player, new ResourceLocation("immortuoscalyx:configupdate"), byteBuf);
    }
}
