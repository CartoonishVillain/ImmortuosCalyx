package com.cartoonishvillain.ImmortuosCalyx.Infection;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class InfectionManagerCapability {
    @CapabilityInject(IInfectionManager.class)
    public static Capability<IInfectionManager> INSTANCE = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IInfectionManager.class);
    }
}
