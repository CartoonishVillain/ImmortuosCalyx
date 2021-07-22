package com.cartoonishvillain.ImmortuosCalyx;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;

public class InternalOrganDamage extends DamageSource {
    public InternalOrganDamage(String p_i1566_1_) {
        super(p_i1566_1_);
    }

    public static DamageSource causeInternalDamage(Entity entity){
        return (new EntityDamageSource("internaldamage", entity)).bypassArmor().bypassMagic();
    }


}
