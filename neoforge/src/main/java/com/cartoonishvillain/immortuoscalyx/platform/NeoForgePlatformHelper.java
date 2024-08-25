package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.player.NeoForgeInfectionPlayerData;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.NeoEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import static com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability.INFECTION_DATA;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public void setInfectionPercentage(ServerPlayer serverPlayer, int infectionPercentage) {
        NeoForgeInfectionPlayerData playerData = serverPlayer.getData(INFECTION_DATA);
        playerData.setInfectionPercent(infectionPercentage);
        serverPlayer.setData(INFECTION_DATA, playerData);
    }

    @Override
    public int getInfectionPercentage(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getInfectionPercent();
    }

    @Override
    public boolean tickInfection(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).tickInfection();
    }

    @Override
    public Holder<MobEffect> INFECTION_BLIND() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_BLIND.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WATER_BREATHING() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_WATER_BREATH.get());
    }
}