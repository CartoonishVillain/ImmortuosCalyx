package com.cartoonishvillain.immortuoscalyx.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.infectedEntityConverter;

@Mixin(value = ServerPlayer.class, priority = 9999999)
public class PlayerInfectionDeathMixin {
    @Inject(at = @At("HEAD"), method = "die")
    private void Immortuosdie(DamageSource damageSource, CallbackInfo ci){
        if(!ci.isCancelled()) {
            LivingEntity entity = ((LivingEntity) (Object) this);
            infectedEntityConverter(damageSource, entity);
        }
    }

}
