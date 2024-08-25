package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ImmortuosServerPlayerTick {
    
    @Inject(at = @At("HEAD"), method = "tick")
    private void ImmortuosPlayerTick(CallbackInfo info) {
        AbstractInfectionHandler.playerTick((ServerPlayer) (Object) this);
    }
}