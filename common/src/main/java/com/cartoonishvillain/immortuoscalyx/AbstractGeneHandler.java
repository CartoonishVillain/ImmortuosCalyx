package com.cartoonishvillain.immortuoscalyx;


import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerPlayer;

public class AbstractGeneHandler {
    public static float turtleDamageHandler(float damageIncoming, int amplitude) {
        return damageIncoming * (0.95f - (0.05f * (float) amplitude));
    }

    public static float frogGeneJumpBoost(float jumpStrength, int amplitude) {
        return jumpStrength + (0.10f + (0.05F * (float) amplitude));
    }

    public static float staggerDamageHandler(float damageDealt, int amplifier) {
        return damageDealt * (1.05f + (0.02f * amplifier));
    }

    public static void tickHeliophobia(ServerPlayer player) {
        if (player.hasEffect(Services.PLATFORM.CONTAMINATION_HELIOPHOBIA())) {
            if (!(player.isInPowderSnow || player.wasInPowderSnow || player.isInWaterRainOrBubble()) && player.level().canSeeSky(player.blockPosition())) {
                player.setRemainingFireTicks(20);
            }
        }
    }

    public static void tickDestablized(ServerPlayer player) {
        if (player.hasEffect(Services.PLATFORM.CONTAMINATION_GENETIC_DESTABILIZATION())) {
            if (player.tickCount % 20 == 0 && player.getRandom().nextInt(500) < 5) {
                player.setRemainingFireTicks(20 * player.getEffect(Services.PLATFORM.CONTAMINATION_GENETIC_DESTABILIZATION()).getAmplifier());
            }
        }
    }
}
