package com.cartoonishvillain.immortuoscalyx.data.player;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class PlayerComponentStarter implements EntityComponentInitializer {

    public static final ComponentKey<PlayerInfectionComponent> INFECTIONCOMPONENTINSTANCE =
            ComponentRegistryV3.INSTANCE.getOrCreate(ResourceLocation.parse("immortuoscalyx:infectiondata"), PlayerInfectionComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(INFECTIONCOMPONENTINSTANCE, PlayerInfectionComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
