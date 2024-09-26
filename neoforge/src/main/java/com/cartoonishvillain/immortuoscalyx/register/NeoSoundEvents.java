package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoSoundEvents {
    private static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    public static DeferredHolder<SoundEvent, SoundEvent> HUMANAMBIENT;
    public static DeferredHolder<SoundEvent, SoundEvent> HUMANHURT;
    public static DeferredHolder<SoundEvent, SoundEvent> HUMANDEATH;
    public static DeferredHolder<SoundEvent, SoundEvent> INJECT;
    public static DeferredHolder<SoundEvent, SoundEvent> EXTRACT;
    public static DeferredHolder<SoundEvent, SoundEvent> SCANBAD ;
    public static DeferredHolder<SoundEvent, SoundEvent> SCANCLEAR;

    public static void init(IEventBus modbus) {
        HUMANAMBIENT = SOUND_EVENT.register("infected_idle", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infected_idle")));
        HUMANHURT = SOUND_EVENT.register("infected_hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infected_hurt")));
        HUMANDEATH = SOUND_EVENT.register("infected_death", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infected_hurt")));
        INJECT = SOUND_EVENT.register("inject", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "inject")));
        EXTRACT = SOUND_EVENT.register("extract", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "extract")));
        SCANBAD = SOUND_EVENT.register("scan_bad", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "scan_bad")));
        SCANCLEAR = SOUND_EVENT.register("scan_clear", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "scan_clear")));
        SOUND_EVENT.register(modbus);
    }
}
