package com.cartoonishvillain.immortuoscalyx.events;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.Spawns;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedIGEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import com.cartoonishvillain.immortuoscalyx.infection.IInfectionManager;
import com.cartoonishvillain.immortuoscalyx.infection.InfectionManagerCapability;
import com.cartoonishvillain.immortuoscalyx.items.ImmortuosSpawnEggItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void capabilityRegister(final RegisterCapabilitiesEvent event){
        event.register(IInfectionManager.class);
        InfectionManagerCapability.INSTANCE = CapabilityManager.get(new CapabilityToken<IInfectionManager>() {});

    }
    @SubscribeEvent
    public static void entityRegister(RegisterEvent event){
        ImmortuosSpawnEggItem.initSpawnEggs();
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> {
            SpawnPlacements.register(Register.INFECTEDDIVER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, InfectedDiverEntity::checkDiverSpawnRules);
            SpawnPlacements.register(Register.INFECTEDHUMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(Register.INFECTEDVILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        });
//        Spawns.PlacementManager();
    }

    @SubscribeEvent
    public static void Attributes(final EntityAttributeCreationEvent event){
            event.put(Register.INFECTEDHUMAN.get(), InfectedHumanEntity.customAttributes().build());
            event.put(Register.INFECTEDDIVER.get(), InfectedDiverEntity.customAttributes().build());
            event.put(Register.INFECTEDVILLAGER.get(), InfectedDiverEntity.customAttributes().build());
            event.put(Register.INFECTEDIG.get(), InfectedIGEntity.customAttributes().build());
            event.put(Register.INFECTEDPLAYER.get(), InfectedPlayerEntity.customAttributes().build());
    }
}
