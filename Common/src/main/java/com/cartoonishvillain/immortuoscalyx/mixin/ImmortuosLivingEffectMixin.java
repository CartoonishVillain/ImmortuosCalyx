package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(LivingEntity.class)
public class ImmortuosLivingEffectMixin {
    @Inject(at = @At("RETURN"), method = "getActiveEffects", cancellable = true)
    public void immortuosActiveEffects(CallbackInfoReturnable<Collection<MobEffectInstance>> cir) {
        Collection<MobEffectInstance> instances = cir.getReturnValue();
                Iterator<MobEffectInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            if (AbstractInfectionHandler.isHiddenImmortuosEffect(iterator.next())) iterator.remove();
        }
        cir.setReturnValue(instances);
    }
}
