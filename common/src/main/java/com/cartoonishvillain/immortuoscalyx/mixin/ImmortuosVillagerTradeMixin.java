package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class ImmortuosVillagerTradeMixin {
    @Inject(at = @At("HEAD"), method = "updateSpecialPrices")
    public void shadyTradeMixin(Player player, CallbackInfo ci) {
        if (player.hasEffect(Services.PLATFORM.CONTAMINATION_SHADY())) {
            MobEffectInstance mobeffectinstance = player.getEffect(Services.PLATFORM.CONTAMINATION_SHADY());
            int k = mobeffectinstance.getAmplifier();

            for (MerchantOffer merchantoffer : ((Villager) (Object) this).getOffers()) {
                double d0 = 0.3 + 0.0625 * (double)k;
                int j = (int)Math.floor(d0 * (double)merchantoffer.getBaseCostA().getCount());
                merchantoffer.addToSpecialPriceDiff(Math.max(j, 1));
            }
        }
    }
}
