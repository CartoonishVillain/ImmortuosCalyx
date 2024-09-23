package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class FabricEntity {
    public static Supplier<EntityType<InfectedHumanEntity>> INFECTED_HUMAN;
    public static Supplier<EntityType<InfectedDiverEntity>> INFECTED_DIVER;

    public static void initEntity() {
        INFECTED_HUMAN = registerHumanEntityType("infected_human", EntityType.Builder.of(InfectedHumanEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("infected_human"));
        INFECTED_DIVER = registerDiverEntityType("infected_diver", EntityType.Builder.of(InfectedDiverEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("infected_diver"));
        FabricDefaultAttributeRegistry.register(INFECTED_HUMAN.get(), InfectedHumanEntity.customAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_DIVER.get(), InfectedDiverEntity.customAttributes());
    }

    private static Supplier<EntityType<InfectedHumanEntity>> registerHumanEntityType(String name, EntityType<?> entityType) {
        EntityType<?> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), entityType);
        return () -> (EntityType<InfectedHumanEntity>) registered;
    }

    private static Supplier<EntityType<InfectedDiverEntity>> registerDiverEntityType(String name, EntityType<?> entityType) {
        EntityType<?> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), entityType);
        return () -> (EntityType<InfectedDiverEntity>) registered;
    }
}
