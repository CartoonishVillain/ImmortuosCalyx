package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ImmortuosJoinMixin {
    @Inject(at = @At("TAIL"), method = "addNewPlayer")
    private void immortuosAddPlayer(ServerPlayer player, CallbackInfo info) {
        AbstractInfectionHandler.inactiveAdditiveSymptomUpdate(player);
    }
}
