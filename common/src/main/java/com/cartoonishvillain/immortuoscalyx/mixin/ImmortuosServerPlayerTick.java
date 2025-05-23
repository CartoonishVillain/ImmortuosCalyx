package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractGeneHandler;
import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ImmortuosServerPlayerTick {
    
    @Inject(at = @At("HEAD"), method = "tick")
    private void ImmortuosPlayerTick(CallbackInfo info) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        AbstractInfectionHandler.playerTick(player);
        AbstractGeneHandler.tickHeliophobia(player);
        if (player.tickCount % 20 == 0) {
            if (player.hasEffect(Services.PLATFORM.CONTAMINATION_HYDROPHOBIA())) {
                if (player.isInWaterRainOrBubble()) {
                    player.hurt(
                            new DamageSource(player.level().registryAccess()
                                    .registryOrThrow(Registries.DAMAGE_TYPE)
                                    .getHolderOrThrow(DamageTypes.DROWN)
                            ), 1);
                }
            }
            AbstractGeneHandler.tickEnderman(player);
            AbstractGeneHandler.tickDestabilized(player);
        }
    }
}