package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionComponent;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
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
}
