package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.configs.CommonConfig;
import com.cartoonishvillain.immortuoscalyx.configs.ConfigHelper;
import com.cartoonishvillain.immortuoscalyx.configs.ServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;

@Mod(Constants.MOD_ID)
public class ForgeImmortuosCalyx {

    public static ArrayList<ResourceLocation> DimensionExclusion;
    public static ServerConfig config;
    public static CommonConfig commonConfig;

    public static MinecraftServer serverInstance;
    
    public ForgeImmortuosCalyx() {
        config = ConfigHelper.register(ModConfig.Type.SERVER, ServerConfig::new);
        commonConfig = ConfigHelper.register(ModConfig.Type.COMMON, CommonConfig::new);
        Register.init();
        CommonImmortuos.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("ImmortuosCalyx") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Register.IMMORTUOSCALYXEGGS.get());
        }
    };

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        serverInstance = event.getServer();
    }
}