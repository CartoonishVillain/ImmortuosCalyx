package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class AntiTrade {

    @Inject(at = @At("HEAD"), method = "interactOn", cancellable = true)
    private void ImmortuosTradeinteractOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir){
        if(!entity.level.isClientSide()){
            if(entity instanceof Villager villager){
                boolean noTrade = false;
                if(Services.PLATFORM.getFollowerStatus((LivingEntity) entity) && Services.PLATFORM.getInfectionProgress((LivingEntity) entity) >= Services.PLATFORM.getFollowerImmunity() * Services.PLATFORM.getVillagerNoTrade()) noTrade = true;
                else if(!Services.PLATFORM.getFollowerStatus((LivingEntity) entity) &&Services.PLATFORM.getInfectionProgress((LivingEntity) entity) >= Services.PLATFORM.getVillagerNoTrade()) noTrade = true;
                if(noTrade) {
                    villager.setUnhappyCounter(40);
                    villager.getCommandSenderWorld().playSound(null, villager.getX(), villager.getY(), villager.getZ(), Services.PLATFORM.getVilIdle(), SoundSource.NEUTRAL, 1f, 1f);
                    cir.cancel();
                }
            }
        }
    }
}

