package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import com.cartoonishvillain.immortuoscalyx.items.HealthScanner;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ImmortuosPlayerAttackMixin {
    @Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    public void ImmortuosAttack(Entity pTargetEntity, CallbackInfo ci) {
        ServerPlayer aggressor = ((ServerPlayer) (Object) this);
        //Contagion
        if (pTargetEntity instanceof ServerPlayer &&
                aggressor.hasEffect(Services.PLATFORM.INFECTION_CONTAGION()) &&
                Services.PLATFORM.getInfectionPercentage((ServerPlayer) pTargetEntity) < 1 &&
                aggressor.gameMode.getGameModeForPlayer() != GameType.SPECTATOR
        ) {
            int aggressorInfectionRate = (Services.PLATFORM.getInfectionPercentage(aggressor)/2); //Infection chance is half the percentage
            AbstractInfectionHandler.infectionCheck((ServerPlayer) pTargetEntity, aggressorInfectionRate);
        }

        // if the user is in the nether, or the player's health and hunger is at or above 10
        if (
                (aggressor.level().getBiome(aggressor.getOnPos()).is(BiomeTags.IS_NETHER)) ||
                        (aggressor.getHealth() >= 10 && aggressor.getFoodData().getFoodLevel() >= 10)
        ) {
            MobEffectInstance instance = aggressor.getEffect(Services.PLATFORM.GENE_WITHER_SKELETON());
            if (instance != null && pTargetEntity instanceof LivingEntity) {
                ((LivingEntity) pTargetEntity).addEffect(
                        new MobEffectInstance(MobEffects.WITHER, Math.clamp(instance.getAmplifier()/2 * 5 + 40, 10, 300), 1)
                );
            }
        }

        //Health Scanner
        if (!pTargetEntity.level().isClientSide() && aggressor.getMainHandItem().getItem() instanceof HealthScanner && pTargetEntity instanceof LivingEntity) {
            AbstractInfectionHandler.foreignHealthCheck((LivingEntity) pTargetEntity, aggressor);
            ci.cancel();
        }
    }
}
