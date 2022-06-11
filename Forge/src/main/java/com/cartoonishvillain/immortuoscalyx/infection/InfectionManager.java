package com.cartoonishvillain.immortuoscalyx.infection;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfectionManager implements IInfectionManager, ICapabilityProvider, INBTSerializable<CompoundTag> {
    protected int infectionProgress = 0;
    protected int infectionTimer = 0;
    protected double resistance = 1;
    protected boolean follower = false;
    protected boolean resistantDosage = false;

    public final LazyOptional<IInfectionManager> holder = LazyOptional.of(()->this);
    @Override
    public int getInfectionProgress() { return this.infectionProgress; } //grabs the infection %
    @Override
    public void setInfectionProgress(int infectionProgress) { this.infectionProgress = infectionProgress; } //sets infection %. Maybe for a command later or something.

    @Override
    public void setInfectionProgressIfLower(int infectionProgress) {
        if(this.infectionProgress < infectionProgress){
            this.infectionProgress = infectionProgress;
        }
    }

    @Override
    public void addInfectionProgress(int infectionProgress) { this.infectionProgress += infectionProgress; } //ticks infection % up
    @Override
    public int getInfectionTimer() {return infectionTimer; }
    @Override
    public void addInfectionTimer(int Time) { this.infectionTimer += Time; }

    @Override
    public void setInfectionTimer(int Time) { infectionTimer = Time; }

    @Override
    public double getResistance() { return resistance; }

    @Override
    public void addResistance(double resistance) { this.resistance += resistance;}

    @Override
    public void setResistance(double resistance) { this.resistance = resistance;}

    @Override
    public void setFollower(boolean isFollower) {
        follower = isFollower;
    }

    @Override
    public boolean isFollower() {
        return follower;
    }

    @Override
    public boolean isResistant() {
        return resistantDosage;
    }

    @Override
    public void setResistant(boolean isResistant) {
        resistantDosage = isResistant;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == InfectionManagerCapability.INSTANCE){ return InfectionManagerCapability.INSTANCE.orEmpty(cap, this.holder); }
        else return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("infectionProgression", infectionProgress);
        tag.putInt("infectionTimer", infectionTimer);
        tag.putDouble("infectionResistance", resistance);
        tag.putBoolean("infectionFollower", follower);
        tag.putBoolean("infectionImpedement", resistantDosage);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        infectionProgress = nbt.getInt("infectionProgression");
        infectionTimer = nbt.getInt("infectionTimer");
        resistance = nbt.getFloat("infectionResistance");
        follower = nbt.getBoolean("infectionFollower");
        resistantDosage = nbt.getBoolean("infectionImpedement");
    }
}
