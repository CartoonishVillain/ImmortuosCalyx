package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class FollowerSpawnMixin {
    @Inject(at = @At("HEAD"), method = "addFreshEntity")
    private void ImmortuosaddFreshEntity(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if(entity instanceof Villager && !((Villager) entity).isBaby()){
            if(entity.level.getRandom().nextInt(Services.PLATFORM.getFollowerChance()) < 2){
                Services.PLATFORM.setInfectionProgressIfLower(1, (LivingEntity) entity);
                Services.PLATFORM.isFollowerStatus(true, (LivingEntity) entity);}
        }
    }
}
