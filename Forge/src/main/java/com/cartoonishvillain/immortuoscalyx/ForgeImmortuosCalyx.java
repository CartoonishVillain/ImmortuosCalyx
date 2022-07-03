package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.Spawns.SpawnModifiers;
import com.cartoonishvillain.immortuoscalyx.configs.CommonConfig;
import com.cartoonishvillain.immortuoscalyx.configs.ConfigHelper;
import com.cartoonishvillain.immortuoscalyx.configs.ServerConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Constants.MOD_ID)
public class ForgeImmortuosCalyx {
    public static ServerConfig config;
    public static CommonConfig commonConfig;

    public static MinecraftServer serverInstance;

    static DeferredRegister<Codec<? extends BiomeModifier>> serializers = DeferredRegister
            .create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    static RegistryObject<Codec<Spawns.SpawnModifiers>> SPAWNCODEC = serializers.register("spawnmodifiers", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    // declare fields
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(SpawnModifiers::biomes),
                    MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn").forGetter(SpawnModifiers::spawn)
                    // declare constructor
            ).apply(builder, SpawnModifiers::new)));

    public ForgeImmortuosCalyx() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        serializers.register(modEventBus);
        config = ConfigHelper.register(ModConfig.Type.SERVER, ServerConfig::new);
        commonConfig = ConfigHelper.register(ModConfig.Type.COMMON, CommonConfig::new);
        Register.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("ImmortuosCalyx") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Register.IMMORTUOSCALYXEGGS.get());
        }
    };

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        serverInstance = event.getServer();
        CommonImmortuos.init();
    }

}