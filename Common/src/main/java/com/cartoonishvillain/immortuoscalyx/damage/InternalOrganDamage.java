package com.cartoonishvillain.immortuoscalyx.damage;

import com.cartoonishvillain.immortuoscalyx.mixin.DamageSourceInvoker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class InternalOrganDamage extends DamageSource {
    public InternalOrganDamage(String p_i1566_1_) {
        super(p_i1566_1_);
    }

    public static DamageSource causeInternalDamage(Entity entity){
        return ((DamageSourceInvoker) new EntityDamageSource("internaldamage", entity)).ImmortuosinvokeBypassArmor();
    }


}
