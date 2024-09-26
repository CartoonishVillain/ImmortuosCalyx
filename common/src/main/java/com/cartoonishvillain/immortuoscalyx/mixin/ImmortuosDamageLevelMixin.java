package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ImmortuosDamageLevelMixin {
    @Inject(at = @At("RETURN"), method = "getDamageAfterMagicAbsorb", cancellable = true)
    public void immortuosGetDamageAfterMagicAbsorb(DamageSource pDamageSource, float pDamageAmount, CallbackInfoReturnable<Float>  cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (
                cir.getReturnValue() == 0.0F || //If the damage is already zero, do not run
                 pDamageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS) ||  //If the damage bypasses enchantments do not run
                  !(entity.hasEffect(Services.PLATFORM.INFECTION_VULNERABLE()) || entity.hasEffect(Services.PLATFORM.INFECTION_RESIST()))) { // If the user doesn't have any modded resistance effect, do not run
        } else {
            //if none of the above is true, run the code based on the effect present
            float damageDealt = cir.getReturnValue();
            if (entity.hasEffect(Services.PLATFORM.INFECTION_RESIST())) damageDealt = damageDealt * 0.75f; //25% Damage reduction
            if (entity.hasEffect(Services.PLATFORM.INFECTION_VULNERABLE())) damageDealt = damageDealt * 1.25f; //25% Damage increase
            cir.setReturnValue(damageDealt);
        }
    }
}
