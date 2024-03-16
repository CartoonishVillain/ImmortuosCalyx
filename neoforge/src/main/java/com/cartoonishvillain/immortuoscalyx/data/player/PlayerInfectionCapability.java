package com.cartoonishvillain.immortuoscalyx.data.player;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class PlayerInfectionCapability {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Constants.MOD_ID);
    public static final Supplier<AttachmentType<NeoForgeInfectionPlayerData>> INFECTION_DATA = ATTACHMENT_TYPE.register(
            "infection_data", () -> AttachmentType.builder(NeoForgeInfectionPlayerData::new).serialize(new InfectionSerializer()).build()
    );
    public static void loadDataAttachment(IEventBus modBus) {
        ATTACHMENT_TYPE.register(modBus);
    }
}
