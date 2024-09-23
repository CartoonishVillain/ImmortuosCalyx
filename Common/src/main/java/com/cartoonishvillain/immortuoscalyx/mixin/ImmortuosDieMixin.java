package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ImmortuosDieMixin {
    @Inject(at = @At("TAIL"), method = "die")
    private void ImmortuosPlayerDie(DamageSource damageSource, CallbackInfo ci) {
        AbstractInfectionHandler.convertPlayer((ServerPlayer) (Object) this, damageSource);
    }
}
