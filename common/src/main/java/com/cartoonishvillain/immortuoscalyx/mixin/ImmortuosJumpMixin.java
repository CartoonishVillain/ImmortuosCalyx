package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractGeneHandler;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ImmortuosJumpMixin {
    @Inject(at = @At("RETURN"), method = "getJumpPower", cancellable = true)
    public void immortuosGetJumpPower(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (entity instanceof Player && entity.hasEffect(Services.PLATFORM.GENE_FROG())) {
            Player player = (Player) entity;
            if (player.isCrouching()) {
                cir.setReturnValue(
                        AbstractGeneHandler.frogGeneJumpBoost(cir.getReturnValue(), entity.getEffect(Services.PLATFORM.GENE_FROG()).getAmplifier())
                );
            }
        }
    }
}
