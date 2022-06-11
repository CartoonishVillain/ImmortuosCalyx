package com.cartoonishvillain.immortuoscalyx.configs;

import com.cartoonishvillain.immortuoscalyx.ForgeImmortuosCalyx;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CommonConfig {
    public static final String SCATEGORY_CLEANSED = "cleansedDimensions";
    public ConfigHelper.ConfigValueListener<String> DIMENSIONALCLEANSE;
    public ConfigHelper.ConfigValueListener<Boolean> HOSTILEINFECTIONINCLEANSE;
    public ConfigHelper.ConfigValueListener<Boolean> PLAYERINFECTIONINCLEANSE;
    public ConfigHelper.ConfigValueListener<Boolean> RAWFOODINFECTIONINCLEANSE;

    public CommonConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.comment("Modify game mechanics based on dimensions").push(SCATEGORY_CLEANSED);
        this.DIMENSIONALCLEANSE = subscriber.subscribe(builder.comment("EXPERIMENTAL! MUST BE ALL CHARACTERS FROM [a-z0-9/._-] OR THE GAME WILL CRASH. List the dimension names that you want the following configs to interact with. (e.g. the_bumblezone:the_bumblezone,minecraft:overworld").define("infectionDimensionCleanse", "notadimension"));
        this.HOSTILEINFECTIONINCLEANSE = subscriber.subscribe(builder.comment("Disables hostile mob attack based infections in cleansed dimensions").define("hostileInfectInCleanse", true));
        this.PLAYERINFECTIONINCLEANSE = subscriber.subscribe(builder.comment("Disables player attack based infections in cleansed dimensions").define("playerInfectInCleanse", false));
        this.RAWFOODINFECTIONINCLEANSE = subscriber.subscribe(builder.comment("Disables raw food infections in cleansed dimensions").define("rawFoodInfectedInCleanse", true));
    }

    public static ArrayList<ResourceLocation> getDimensions() {
        final String DimensionList = ForgeImmortuosCalyx.commonConfig.DIMENSIONALCLEANSE.get();
        String[] DimensionExclusion = DimensionList.split(",");
        int exclusionLength = DimensionExclusion.length;
        ArrayList<ResourceLocation> finalDimensionExclusion = new ArrayList<>();
        int counter = 0;
        for (String i : DimensionExclusion) {
            ResourceLocation newResource = new ResourceLocation(i);
            finalDimensionExclusion.add(newResource);
            counter++;
        }
        return finalDimensionExclusion;
    }

}
