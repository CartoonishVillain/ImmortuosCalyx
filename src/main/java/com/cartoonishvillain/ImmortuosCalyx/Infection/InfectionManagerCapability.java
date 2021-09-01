package com.cartoonishvillain.ImmortuosCalyx.Infection;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class InfectionManagerCapability {
    @CapabilityInject(IInfectionManager.class)
    public static Capability<IInfectionManager> INSTANCE = null;

}
