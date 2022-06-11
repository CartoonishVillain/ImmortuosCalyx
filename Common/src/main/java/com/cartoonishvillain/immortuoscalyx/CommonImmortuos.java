package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Arrays;

public class CommonImmortuos {

    // This method serves as an initialization hook for the mod. The vanilla
    // game has no mechanism to load tooltip listeners so this must be
    // invoked from a mod loader specific project like Forge or Fabric.
    public static ArrayList<ResourceLocation> DimensionExclusion;
    public static final ArrayList<Item> rawItem = new ArrayList<>(Arrays.asList(Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COD, Items.SALMON, Items.ROTTEN_FLESH));

    public static void init() {
        DimensionExclusion = Services.PLATFORM.getDimensions();
    }

}