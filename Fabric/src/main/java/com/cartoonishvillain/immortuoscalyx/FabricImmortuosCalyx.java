package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionRateCommand;
import com.cartoonishvillain.immortuoscalyx.config.ImmortuosConfig;
import com.cartoonishvillain.immortuoscalyx.networking.ConfigPacket;
import io.netty.buffer.Unpooled;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.cartoonishvillain.immortuoscalyx.Spawns.initSpawns;

public class FabricImmortuosCalyx implements ModInitializer {
    public static ImmortuosConfig config;
    public static final CreativeModeTab TAB = FabricItemGroupBuilder.build(new ResourceLocation(Constants.MOD_ID, "immortuostab"), () -> new ItemStack(Register.IMMORTUOSCALYXEGGS));


    @Override
    public void onInitialize() {
        AutoConfig.register(ImmortuosConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ImmortuosConfig.class).getConfig();

        Register.init();

        CommonImmortuos.init();

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            SetInfectionRateCommand.register(dispatcher);
        }));

        registerPackets();

        initSpawns();

        ServerPlayConnectionEvents.JOIN.register(JoinListener.getInstance());

        SpawnRestrictionAccessor.callRegister(Register.INFECTEDDIVER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, Mob::checkMobSpawnRules);
        SpawnRestrictionAccessor.callRegister(Register.INFECTEDHUMAN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnRestrictionAccessor.callRegister(Register.INFECTEDVILLAGER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
    }

    public static class JoinListener implements ServerPlayConnectionEvents.Join{
        private static final JoinListener INSTANCE = new JoinListener();
        @Override
        public void onPlayReady(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
            ConfigPacket.send(handler.player, new FriendlyByteBuf(Unpooled.buffer()));
        }
        public static JoinListener getInstance() {return INSTANCE;}

    }

    public static ArrayList<ResourceLocation> getDimensions() {
        final String DimensionList = config.dimensionsAndSpawnDetails.DIMENSIONALCLEANSE;
        String[] DimensionExclusion = DimensionList.split(",");
        int exclusionLength = DimensionExclusion.length;
        ArrayList<ResourceLocation> finalDimensionExclusion = new ArrayList<>();
        int counter = 0;
        for (String i : DimensionExclusion) {
            ResourceLocation newResource = new ResourceLocation(i);
            finalDimensionExclusion.add(newResource);
            counter++;
        }
        return finalDimensionExclusion;
    }


    public static void registerPackets(){
        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(Constants.MOD_ID, "soundpacket"),((server, player, handler, buf, responseSender) -> {
            player.level.playSound(null, player.blockPosition(), Register.HUMANAMBIENT, SoundSource.PLAYERS, 0.5f, 2f);
        }));

        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(Constants.MOD_ID, "chatpacket"), (((server, player, handler, buffer, responseSender) -> {
            int length = buffer.readInt();
            String name = (String) buffer.readCharSequence(length, Charset.defaultCharset());
            length = buffer.readInt();
            String message = (String) buffer.readCharSequence(length, Charset.defaultCharset());
            server.getPlayerList().broadcastMessage(new TextComponent(name + ChatFormatting.OBFUSCATED + message), ChatType.CHAT, player.getUUID());
        })));
    }
}
