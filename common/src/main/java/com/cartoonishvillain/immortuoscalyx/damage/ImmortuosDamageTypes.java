package com.cartoonishvillain.immortuoscalyx.damage;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ImmortuosDamageTypes {
    public static final ResourceKey<DamageType> infection_damage = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"infection_damage"));
    public static final ResourceKey<DamageType> organ_damage = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "organ_damage"));
}
