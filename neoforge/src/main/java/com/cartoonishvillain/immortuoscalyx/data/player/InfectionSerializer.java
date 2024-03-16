package com.cartoonishvillain.immortuoscalyx.data.player;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.Nullable;

public class InfectionSerializer implements IAttachmentSerializer<CompoundTag, NeoForgeInfectionPlayerData> {
    @Override
    public NeoForgeInfectionPlayerData read(IAttachmentHolder holder, CompoundTag tag) {
        NeoForgeInfectionPlayerData playerData = new NeoForgeInfectionPlayerData();
        playerData.setTicks(tag.getInt("immortuosTicks"));
        playerData.setInfectionPercent(tag.getInt("immortuosPercent"));
        return playerData;
    }

    @Override
    public @Nullable CompoundTag write(NeoForgeInfectionPlayerData attachment) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("immortuosTicks", attachment.getTicks());
        compoundTag.putInt("immortuosPercent", attachment.getInfectionPercent());
        return compoundTag;
    }
}
