package com.cartoonishvillain.immortuoscalyx.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.infectedEntityConverter;

@Mixin(LivingEntity.class)
public class InfectionDeathMixin {
    @Inject(at = @At("HEAD"), method = "die")
    private void ImmortuosMobdie(DamageSource damageSource, CallbackInfo ci){
        LivingEntity entity = ((LivingEntity) (Object) this);
        infectedEntityConverter(damageSource, entity);
    }

}
