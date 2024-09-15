package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.client.telemetry.TelemetryProperty;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ImmortuosPlayerAttackMixin {
    @Inject(at = @At("HEAD"), method = "attack")
    public void ImmortuosAttack(Entity pTargetEntity, CallbackInfo ci) {
        ServerPlayer aggressor = ((ServerPlayer) (Object) this);
        if (pTargetEntity instanceof ServerPlayer &&
                aggressor.hasEffect(Services.PLATFORM.INFECTION_CONTAGION()) &&
                Services.PLATFORM.getInfectionPercentage((ServerPlayer) pTargetEntity) < 1 &&
                aggressor.gameMode.getGameModeForPlayer() != GameType.SPECTATOR
        ) {
            int aggressorInfectionRate = (Services.PLATFORM.getInfectionPercentage(aggressor)/2); //Infection chance is half the percentage
            int armorResistance = ((ServerPlayer) pTargetEntity).getArmorValue() * 2; //Each armor value reduces infection chance by 2%
            //TODO ADD ANTIBIOTIC RESISTANCE
            int finalInfectionRate = aggressorInfectionRate - armorResistance;
            if (finalInfectionRate < 1) finalInfectionRate = 1; //finalInfectionRate is minimum 1.
            if (pTargetEntity.getRandom().nextInt(100) <= finalInfectionRate) { //if our random roll is less than or equal to the infection rate, we infect the target player.
                Services.PLATFORM.setInfectionPercentage((ServerPlayer) pTargetEntity, 1);
                aggressor.level().playSound(null, aggressor.getOnPos().above(1), Services.PLATFORM.HUMANOID_HURT(), SoundSource.PLAYERS);
            }
        }
    }
}
