package com.cartoonishvillain.immortuoscalyx.data.player;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class PlayerComponentStarter implements EntityComponentInitializer {

    public static final ComponentKey<PlayerInfectionComponent> INFECTIONCOMPONENTINSTANCE =
            ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation("immortuoscalyx:infectiondata"), PlayerInfectionComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(INFECTIONCOMPONENTINSTANCE, PlayerInfectionComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
