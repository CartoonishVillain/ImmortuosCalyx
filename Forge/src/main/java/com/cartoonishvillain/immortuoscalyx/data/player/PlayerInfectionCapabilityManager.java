package com.cartoonishvillain.immortuoscalyx.data.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerInfectionCapabilityManager implements IPlayerInfectionCapability, ICapabilityProvider, INBTSerializable<CompoundTag> {
    public final LazyOptional<IPlayerInfectionCapability> holder = LazyOptional.of(()->this);
    //Todo: set up config for infectionTicks default
    int infectionPercent = 0;
    int infectionTicks = 110;

    @Override
    public int getInfectionPercent() {
        return infectionPercent;
    }

    @Override
    public void setInfectionPercent(int infectionPercent) {
        this.infectionPercent = infectionPercent;
    }

    @Override
    public boolean tickInfection() {
        boolean changedPercent = false;
        infectionTicks--;
        if (infectionPercent > 0) {
            if (infectionTicks <= 0) {
                infectionTicks = 110;
                if (infectionPercent < 100) {
                    infectionPercent++;
                    changedPercent = true;
                }
            }
        }
        return changedPercent;
    }

    @Override
    public int getTicks() {
        return infectionTicks;
    }

    @Override
    public void setTicks(int ticks) {
        infectionTicks = ticks;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerInfectionCapability.INSTANCE){ return PlayerInfectionCapability.INSTANCE.orEmpty(cap, this.holder); }
        else return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("immortuosTicks", infectionTicks);
        tag.putInt("immortuosPercent", infectionPercent);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt != null) {
            infectionTicks = nbt.getInt("immortuosTicks");
            infectionPercent = nbt.getInt("immortuosPercent");
        }
    }
}
