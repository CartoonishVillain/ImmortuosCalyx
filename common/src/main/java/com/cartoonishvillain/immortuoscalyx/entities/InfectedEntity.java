package com.cartoonishvillain.immortuoscalyx.entities;

import net.minecraft.world.entity.LivingEntity;

public interface InfectedEntity {

     default boolean shouldAttackMonster(LivingEntity entity) {
        if(entity != null){
            return !(entity instanceof InfectedEntity);
        }else return false;
    }
}
