package com.cartoonishvillain.immortuoscalyx.mixin;

import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Player.class)
public interface PlayerAbilitiesAccessor {
    @Accessor
    Abilities getAbilities();
}
