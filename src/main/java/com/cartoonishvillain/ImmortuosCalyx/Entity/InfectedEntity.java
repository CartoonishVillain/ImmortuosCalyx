package com.cartoonishvillain.ImmortuosCalyx.Entity;

import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;

public interface InfectedEntity {

     default boolean shouldAttackMonster(@Nullable LivingEntity entity) {
        if(entity != null){
            return !(entity instanceof InfectedEntity);
        }else return false;
    }
}
