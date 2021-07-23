package com.cartoonishvillain.ImmortuosCalyx.Events;

import com.cartoonishvillain.ImmortuosCalyx.Entity.*;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Items.ImmortuosSpawnEggItem;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void entityRegister(final RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(Register.INFECTEDDIVER.get(), Register.INFECTEDHUMAN.get(), Register.INFECTEDIG.get(), Register.INFECTEDPLAYER.get(), Register.INFECTEDVILLAGER.get());
        Spawns.PlacementManager();
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event){
        ImmortuosSpawnEggItem.initSpawnEggs();
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
