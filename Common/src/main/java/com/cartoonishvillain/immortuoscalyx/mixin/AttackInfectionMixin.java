package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.component.ComponentTicker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class AttackInfectionMixin {

    @Inject(at = @At("HEAD"), method = "actuallyHurt")
    private void Immortuosattack(DamageSource damageSource, float f, CallbackInfo ci) {
        LivingEntity victim = ((LivingEntity) (Object) this);
        if (!victim.level.isClientSide) {
            if (damageSource.getEntity() != null && damageSource.getEntity() instanceof LivingEntity aggro) {
                ComponentTicker.attackHelper(aggro, victim);
            }
        }
    }
}
