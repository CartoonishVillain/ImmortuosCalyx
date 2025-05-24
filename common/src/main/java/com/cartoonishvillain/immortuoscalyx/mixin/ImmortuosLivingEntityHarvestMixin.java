package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractGeneHandler;
import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class ImmortuosLivingEntityHarvestMixin {
    @Inject(at = @At("TAIL"), method = "die")
    private void ImmortuosPlayerDie(DamageSource damageSource, CallbackInfo ci) {
        AbstractInfectionHandler.checkForHarvest((LivingEntity) (Object) this, damageSource);

        AbstractGeneHandler.vindicatorGeneCheck(damageSource);
    }
}
