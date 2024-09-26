package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectUtil.class)
public class ImmortuosEffectUtilMixin {
    @Inject(at = @At("HEAD"), method = "hasWaterBreathing", cancellable = true)
    private static void ImmortuosWaterBreathing(LivingEntity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity.hasEffect(Services.PLATFORM.INFECTION_WATER_BREATHING())) cir.setReturnValue(true);
    }
}
