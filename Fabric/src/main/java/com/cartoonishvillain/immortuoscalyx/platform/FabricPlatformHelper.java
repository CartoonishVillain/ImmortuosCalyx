package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionComponent;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.FabricEffects;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;

import static com.cartoonishvillain.immortuoscalyx.data.player.PlayerComponentStarter.INFECTIONCOMPONENTINSTANCE;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void setInfectionPercentage(ServerPlayer serverPlayer, int infectionPercentage) {
        INFECTIONCOMPONENTINSTANCE.get(serverPlayer).setInfectionPercent(infectionPercentage);
    }

    @Override
    public int getInfectionPercentage(ServerPlayer serverPlayer) {
        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).getInfectionPercent();
    }

    @Override
    public boolean tickInfection(ServerPlayer serverPlayer) {
        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).tickInfection();
    }

    @Override
    public Holder<MobEffect> INFECTION_BLIND() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_BLIND.get());
    }
}
