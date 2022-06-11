package com.cartoonishvillain.immortuoscalyx.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "immortuos")
public class ImmortuosConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public PlayerToggles playerToggles = new PlayerToggles();

    @ConfigEntry.Gui.CollapsibleObject
    public ContagionConfig contagionConfig = new ContagionConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public PlayerSymptomProgression playerSymptomProgression = new PlayerSymptomProgression();

    @ConfigEntry.Gui.CollapsibleObject
    public EntityToggles entityToggles = new EntityToggles();

    @ConfigEntry.Gui.CollapsibleObject
    public OtherDetails otherDetails = new OtherDetails();

    @ConfigEntry.Gui.CollapsibleObject
    public DimensionsAndSpawnDetails dimensionsAndSpawnDetails = new DimensionsAndSpawnDetails();

    public static class PlayerToggles{

        @Comment("Does chat get removed or altered by a heavy infection (40% infection by default)")
        public boolean ANTICHAT = true;
        @Comment("Do players make noise when they try to chat with a heavy infection (40% infection by default)")
        public boolean INFECTEDCHATNOISE = true;
        @Comment("Do players infect others on attack (50% infection by default)")
        public boolean PVPCONTAGION = true;
        @Comment("Do players get slowed down in hot environments? (60% infection by default)")
        public boolean HEATSLOW = true;
        @Comment("Do players get sped up in cold environments? (60% infection by default)")
        public boolean COLDFAST = true;
        @Comment("Enables or disables water breathing for players at 67%")
        public boolean WATERBREATHING = true;
        @Comment("Enables or disables conduit power for players at 67%, in cold biomes, while also in the water.")
        public boolean COLDCONDUITPOWER = true;
        @Comment("Are players weakened in all but the coldest environments? (80% infection by default)")
        public boolean WARMWEAKNESS = true;
        @Comment("Are players strengthend in cold environments? (80% infection by default)")
        public boolean COLDSTRENGTH = true;
        @Comment("Are players blinded by heavy infections? (95% infection by default)")
        public boolean BLINDNESS = true;
    }

    public static class ContagionConfig{
        @Comment("Multiplier to how much armor can block infections in combat")
        public double ARMORRESISTMULTIPLIER = 2;
        @Comment("Resistance Multiplier given by general antiparasitic")
        public double RESISTGIVENAP = 6;
        @Comment("Infection rate of a fully infected entity attacking a player")
        public int INFECTEDENTITYINFECTIONVALUE = 90;
        @Comment("Infection rate of your average every day zombie attacking a player")
        public int ZOMBIEINFECTIONVALUE = 20;
        @Comment("Infection rate of eating raw food")
        public int RAWFOODINFECTIONVALUE = 10;
    }

    public static class PlayerSymptomProgression{
        @Comment("Changes when the first warning message for the infection will send")
        public int EFFECTMESSAGEONE = 10;
        @Comment("Changes when the second warning message for the infection will send")
        public int EFFECTMESSAGETWO = 25;
        @Comment("Changes when the chat blocking side effect occurs")
        public int EFFECTCHAT = 40;
        @Comment("Changes where players can start infecting each other in infection percentage")
        public int PLAYERINFECTIONTHRESHOLD = 50;
        @Comment("Changes when the speed/slowdown side effects occurs")
        public int EFFECTSPEED = 60;
        @Comment("Changes when the water breathing side effects occurs")
        public int EFFECTWATERBREATH = 67;
        @Comment("Changes when the strength/weakness side effects occurs")
        public int EFFECTSTRENGTH = 85;
        @Comment("Changes when the blindness side effect will occur")
        public int EFFECTBLIND = 95;
        @Comment("Changes when players will start being damaged by the parasite")
        public int EFFECTDAMAGE = 100;
        @Comment("Changes when players will stop gaining infection when injected with a stablized sample.")
        public int EFFECTIMPEDIMENT = 89;
    }

    public static class EntityToggles{
        @Comment("Chance a newly generated, non-baby villager will be a follower intentionally carrying the Immortuos Calyx Parasite. Higher numbers increase rarity")
        public int VILLAGERFOLLOWERCHANCE = 25;
        @Comment("Multiplier for how much more a follower can bear infection compared to the average villager (before symptoms show up)")
        public int VILLAGERFOLLOWERIMMUNITY = 2;
        @Comment("The infection percentage for a villager needed before they get Slowness I")
        public int VILLAGERSLOWONE = 5;
        @Comment("The infection percentage for a villager needed before they get Slowness II")
        public int VILLAGERSLOWTWO = 25;
        @Comment("The infection percentage for a villager to stop trading with players")
        public int VILLAGERNOTRADE = 37;
        @Comment("The lethal infection percentage for villains")
        public int VILLAGERLETHAL = 60;
        @Comment("The infection percentage for an iron golem needed before they get Slowness I")
        public int IRONGOLEMSLOW = 30;
        @Comment("The infection percentage for an iron golem needed before they get Weakness I")
        public int IRONGOLEMWEAK = 30;
        @Comment("The lethal infection percentage for iron golems")
        public int IRONGOLEMLETHAL = 110;
    }

    public static class OtherDetails{
        @Comment("How much being injected with Immortuos Calyx eggs starts you off in infection %")
        public int EGGINFECTIONSTART = 1;
        @Comment("How much damage the parasite does when consuming an entity")
        public int INFECTIONDAMAGE = 1;
        @Comment("How much of the parasite will shed off a given player when infecting another player")
        public int PVPCONTAGIONRELIEF = 5;
        @Comment("Infection % of someone starting the infection via pvp")
        public int PVPCONTAGIONAMOUNT = 1;
        @Comment("How long it takes to increase 1% in infection level in ticks (20 per second assuming no lag)")
        public int INFECTIONTIMER = 450;
        @Comment("An alternative to blocking out chat entirely when infected. Will turn a player's chat to gibberish instead")
        public boolean FORMATTEDINFECTCHAT = false;
        @Comment("Enables a chance for the infection to turn a victim into an infected variant regardless of the reason of death, outside of extreme cases such as explosions.")
        public boolean INFECTIONDEATH = true;
    }

    public static class DimensionsAndSpawnDetails{
        @Comment("The spawn weight of infected villagers. Higher is more frequent")
        public int VILLAGER = 1;
        @Comment("The spawn weight of infected divers. Higher is more frequent")
        public int DIVER = 1;
        @Comment("The spawn weight of infected humans. Higher is more frequent")
        public int HUMAN = 5;
        @Comment("EXPERIMENTAL! MUST BE ALL CHARACTERS FROM [a-z0-9/._-] OR THE GAME WILL CRASH. List the dimension names that you want the following configs to interact with. (e.g. the_bumblezone:the_bumblezone,minecraft:overworld)")
        public String DIMENSIONALCLEANSE = "notadimension";
        @Comment("Disables hostile mob attack based infections in cleansed dimensions")
        public boolean HOSTILEINFECTIONINCLEANSE = true;
        @Comment("Disables player attack based infections in cleansed dimensions")
        public boolean PLAYERINFECTIONINCLEANSE = false;
        @Comment("Disables raw food infections in cleansed dimensions")
        public boolean RAWFOODINFECTIONINCLEANSE = true;
    }


}
