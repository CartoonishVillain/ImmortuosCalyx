package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.allay.Allay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoEntity {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);

    public static DeferredHolder<EntityType<?>, EntityType<InfectedHumanEntity>> INFECTEDHUMAN;
    public static DeferredHolder<EntityType<?>, EntityType<InfectedDiverEntity>> INFECTEDDIVER;


    public static void init(IEventBus modbus) {
        INFECTEDHUMAN = ENTITY_TYPES.register("infected_human", () -> EntityType.Builder.of(InfectedHumanEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("infected_human"));
        INFECTEDDIVER = ENTITY_TYPES.register("infected_diver", () -> EntityType.Builder.of(InfectedDiverEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("infected_diver"));
        ENTITY_TYPES.register(modbus);
    }
}
