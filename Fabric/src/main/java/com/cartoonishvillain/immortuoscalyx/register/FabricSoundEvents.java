package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class FabricSoundEvents {
    public static Supplier<SoundEvent> HUMANAMBIENT;
    public static Supplier<SoundEvent> HUMANHURT;
    public static Supplier<SoundEvent> HUMANDEATH;
    public static Supplier<SoundEvent> INJECT;
    public static Supplier<SoundEvent> EXTRACT;
    public static Supplier<SoundEvent> SCANBAD ;
    public static Supplier<SoundEvent> SCANCLEAR;

    public static void initSounds() {
        HUMANAMBIENT = registerSound("infected_idle",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infected_idle")));
        HUMANHURT = registerSound("infected_hurt",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infected_hurt")));
        HUMANDEATH = registerSound("infected_death",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infected_hurt")));
        INJECT = registerSound("inject",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "inject")));
        EXTRACT = registerSound("extract",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "extract")));
        SCANBAD = registerSound("scan_bad",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "scan_bad")));
        SCANCLEAR = registerSound("scan_clear",  SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "scan_clear")));
    }

    private static Supplier<SoundEvent> registerSound(String name, SoundEvent effect) {
        SoundEvent registered = Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), effect);
        return () -> registered;
    }
}
