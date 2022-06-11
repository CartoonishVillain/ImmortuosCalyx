package com.cartoonishvillain.immortuoscalyx.mixin;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceInvoker {

    @Invoker("bypassArmor")
    public DamageSource ImmortuosinvokeBypassArmor();

}
