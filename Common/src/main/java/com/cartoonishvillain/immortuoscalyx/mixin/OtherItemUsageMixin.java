package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.items.BaseItems;
import com.cartoonishvillain.immortuoscalyx.items.ItemFunctionality;
import com.cartoonishvillain.immortuoscalyx.component.ItemUsages;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class OtherItemUsageMixin {


    @Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    private void ImmortuosSyringeattack(Entity entity, CallbackInfo ci){
        Player player = (Player) (Object) this;
        if(!entity.level.isClientSide() && player.getMainHandItem().getItem() instanceof BaseItems && entity instanceof LivingEntity){
            if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.ANTIBIOTIC)){
                ItemUsages.useAntiParasitic(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.CALYXIDE)){
                ItemUsages.useCalyxide(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.EGGS)){
                ItemUsages.useImmortuosCalyxEggs(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.SCANNER)){
                ItemUsages.useScanner((LivingEntity) entity, player);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.STABILIZE)){
                ItemUsages.useStabilize(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            }
        }
    }

}
