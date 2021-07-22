package com.cartoonishvillain.ImmortuosCalyx.Infection;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class InfectionManagerCapability {
    @CapabilityInject(IInfectionManager.class)
    public static Capability<IInfectionManager> INSTANCE = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IInfectionManager.class, new Capability.IStorage<IInfectionManager>() {
            @Nullable
            @Override
            public Tag writeNBT(Capability<IInfectionManager> capability, IInfectionManager instance, Direction side) {
                CompoundTag tag = new CompoundTag();
                tag.putInt("infectionProgression", instance.getInfectionProgress());
                tag.putInt("infectionTimer", instance.getInfectionTimer());
                tag.putDouble("infectionResistance", instance.getResistance());
                return tag;
            }

            @Override
            public void readNBT(Capability<IInfectionManager> capability, IInfectionManager instance, Direction side, Tag nbt) {
                CompoundTag tag = (CompoundTag) nbt;
                instance.setInfectionProgress(tag.getInt("infectionProgression"));
                instance.setInfectionTimer(tag.getInt("infectionTimer"));
                instance.setResistance(tag.getDouble("infectionResistance"));
            }
        }, new Callable<InfectionManager>(){
            @Override
            public InfectionManager call() throws Exception {
                return new InfectionManager();
            }

        });
    }
}
