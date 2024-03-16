package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
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
        serverPlayer.getCapability(PlayerInfectionCapability.INSTANCE).ifPresent(h -> h.setInfectionPercent(infectionPercentage));
    }

    @Override
    public int getInfectionPercentage(ServerPlayer serverPlayer) {
        AtomicInteger infectPercentage = new AtomicInteger(0);
        serverPlayer.getCapability(PlayerInfectionCapability.INSTANCE).ifPresent(h -> infectPercentage.set(h.getInfectionPercent()));
        return infectPercentage.get();
    }

    @Override
    public boolean tickInfection(ServerPlayer serverPlayer) {
        AtomicBoolean updateInfection = new AtomicBoolean(false);
        serverPlayer.getCapability(PlayerInfectionCapability.INSTANCE).ifPresent(h -> updateInfection.set(h.tickInfection()));
        return updateInfection.get();
    }
}