package com.cartoonishvillain.ImmortuosCalyx;


import com.cartoonishvillain.ImmortuosCalyx.Configs.CommonConfig;
import com.cartoonishvillain.ImmortuosCalyx.Configs.ConfigHelper;
import com.cartoonishvillain.ImmortuosCalyx.Configs.ServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("immortuoscalyx")
public class ImmortuosCalyx
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "immortuoscalyx";
    public static ArrayList<ResourceLocation> DimensionExclusion;
    public static ServerConfig config;
    public static CommonConfig commonConfig;
    public ImmortuosCalyx() {
        config = ConfigHelper.register(ModConfig.Type.SERVER, ServerConfig::new);
        commonConfig = ConfigHelper.register(ModConfig.Type.COMMON, CommonConfig::new);
        // Register the setup method for modloading
        Register.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call

    public static final CreativeModeTab TAB = new CreativeModeTab("ImmortuosCalyx") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Register.IMMORTUOSCALYXEGGS.get());
        }
    };

}
