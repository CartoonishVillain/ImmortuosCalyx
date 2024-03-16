package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.data.player.IPlayerInfectionCapability;
import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class ForgeImmortuos {
    
    public ForgeImmortuos() {
        CommonImmortuos.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::capabilityRegister);
    }

    public void capabilityRegister(final RegisterCapabilitiesEvent event){
        event.register(IPlayerInfectionCapability.class);
        PlayerInfectionCapability.INSTANCE = CapabilityManager.get(new CapabilityToken<IPlayerInfectionCapability>() {});
    }
}