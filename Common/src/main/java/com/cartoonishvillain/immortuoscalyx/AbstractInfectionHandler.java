package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerPlayer;

public class AbstractInfectionHandler {
    public static void playerTick(ServerPlayer player) {
        Services.PLATFORM.tickInfection(player);
    }
}
