package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class ImmortuosPlayerExhaustMixin {
    @Inject(at = @At("HEAD"), method = "causeFoodExhaustion", cancellable = true)
    public void ImmortuosAttack(float pExhaustion, CallbackInfo ci) {
        Player user = ((Player) (Object) this);
        if (user instanceof ServerPlayer &&
                user.hasEffect(Services.PLATFORM.GENE_WITHER_SKELETON()) &&
                (!(((PlayerAbilitiesAccessor) user).getAbilities().invulnerable) && !user.level().isClientSide)
        ) {
            //25% additional food exhaustion added
            user.getFoodData().addExhaustion(pExhaustion/4);
        }
    }
}
