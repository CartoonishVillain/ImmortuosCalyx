package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.component.ComponentTicker;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityTickMixin {
    @Inject(at = @At("TAIL"), method = "tick()V")
    private void Immortuostick(CallbackInfo info) {
        if(!((LivingEntity) (Object) this).level.isClientSide)
        ComponentTicker.tickEntity((LivingEntity) (Object) this);
    }
}

