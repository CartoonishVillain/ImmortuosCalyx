package com.cartoonishvillain.immortuoscalyx.config;

public class ImmortuosConfig  {

    public static String provider(String filename) {
        return "#Does chat get removed or altered by a heavy infection (40% infection by default)\n" +
                "ANTICHAT=true\n" +
                "#Do players make noise when they try to chat with a heavy infection (40% infection by default)\n" +
                "INFECTEDCHATNOISE=true\n" +
                "#Do players infect others on attack (50% infection by default)\n" +
                "PVPCONTAGION=true\n" +
                "#Do players get slowed down in hot environments? (60% infection by default)\n" +
                "HEATSLOW=true\n" +
                "#Do players get sped up in cold environments? (60% infection by default)\n" +
                "COLDFAST=true\n" +
                "#Enables or disables water breathing for players at 67%\n" +
                "WATERBREATHING=true\n" +
                "#Enables or disables conduit power for players at 67%, in cold biomes, while also in the water.\n" +
                "COLDCONDUITPOWER=true\n" +
                "#Are players weakened in all but the coldest environments? (80% infection by default)\n" +
                "WARMWEAKNESS=true\n" +
                "#Are players strengthend in cold environments? (80% infection by default)\n" +
                "COLDSTRENGTH=true\n" +
                "#Are players blinded by heavy infections? (95% infection by default)\n" +
                "BLINDNESS=true\n" +
                "#Multiplier to how much armor can block infections in combat\n" +
                "ARMORRESISTMULTIPLIER=2\n" +
                "#Resistance Multiplier given by general antiparasitic\n" +
                "RRESISTGIVENAP=6\n" +
                "#Infection rate of a fully infected entity attacking a player\n" +
                "INFECTEDENTITYINFECTIONVALUE=90\n" +
                "#Infection rate of your average every day zombie attacking a player\n" +
                "ZOMBIEINFECTIONVALUE=20\n" +
                "#Infection rate of eating raw food\n" +
                "RAWFOODINFECTIONVALUE=10\n" +
                "#Changes when the first warning message for the infection will send\n" +
                "EFFECTMESSAGEONE=10\n" +
                "#Changes when the second warning message for the infection will send\n" +
                "EFFECTMESSAGETWO = 25\n" +
                "#Changes when the chat blocking side effect occurs\n" +
                "EFFECTCHAT=40\n" +
                "#Changes when players can start infecting each other\n" +
                "PLAYERINFECTIONTHRESHOLD=50\n" +
                "#Changes when speed/slowdown side effects occur\n" +
                "EFFECTSPEED=60\n" +
                "#Changes when water breathing side effects occur\n" +
                "EFFECTWATERBREATH=67\n" +
                "#Changes when the strength/weakness side effects occur\n" +
                "EFFECTSTRENGTH=85\n" +
                "#Changes when the blindness side effect will occur\n" +
                "EFFECTBLIND=95\n" +
                "#Changes when players will start being damaged by the parasite\n" +
                "EFFECTDAMAGE=100\n" +
                "#Changes when players will stop gaining infection when injected with a stabilized sample\n" +
                "EFFECTIMPEDIMENT=89\n" +
                "#Chance a newly generated, non-baby villager will be a follower intentionally carrying the Immortuos Calyx Parasite. Higher numbers increase rarity\n" +
                "VILLAGERFOLLOWERCHANCE=25\n" +
                "#Multiplier for how much more a follower can bear infection compared to the average villager (before symptoms show up)\n" +
                "VILLAGERFOLLOWERIMMUNITY=2\n" +
                "#The infection percentage for a villager needed before they get Slowness I\n" +
                "VILLAGERSLOWONE=5\n" +
                "#The infection percentage for a villager needed before they get Slowness II\n" +
                "VILLAGERSLOWTWO=15\n" +
                "#The infectioj percentage for a villager to stop trading with players\n" +
                "VILLAGERNOTRADE=37\n" +
                "#The lethal infection percentage for villagers\n" +
                "VILLAGERLETHAL=60\n" +
                "#The infection percentage for an iron golem needed before they get Slowness I\n" +
                "IRONGOLEMSLOW=30\n" +
                "#The infection percentage for an iron golem needed before they get Weakness I\n" +
                "IRONGOLEMWEAK=30\n" +
                "#The lethal infection percentage for an iron golem\n" +
                "IRONGOLEMLETHAL=110\n" +
                "#How much being injected with Immortuos Calyx eggs starts you off in infection %\n" +
                "EGGEINFECTIONSTART=1\n" +
                "#How much damage the parasite does when consuming an entity\n" +
                "INFECTIONDAMAGE=1\n" +
                "#How much of the parasite will shed off a given player when infecting another player\n" +
                "PVPCONTAGIONRELIEF=5\n" +
                "#Infection % of someone starting the infection via pvp\n" +
                "PVPCONTAGIONAMOUNT=1\n" +
                "#How long it takes to increase 1% in infection level in ticks (20 per second assuming no lag)\n" +
                "INFECTIONTIMER=450\n" +
                "#An alternative to blocking out chat entirely when infected. Will turn a player's chat to gibberish instead\n" +
                "FORMATTEDINFECTCHAT=false\n" +
                "#Enables a chance for the infection to turn a victim into an infected variant regardless of the reason of death, outside of extreme cases such as explosions.\n" +
                "INFECTIONDEATH=true\n" +
                "#The spawn weight of infected villagers. Higher is more frequent\n" +
                "VILLAGER=1\n" +
                "#The spawn weight of infected dives. Higher is more frequent\n" +
                "DIVER=1\n" +
                "#The spawn weight of infected humans. Higher is more frequent\n" +
                "HUMAN=5\n" +
                "#EXPERIMENTAL! MUST BE ALL CHARACTERS FROM [a-z0-9/._-] OR THE GAME WILL CRASH. List the dimension names that you want the following configs to interact with. (e.g. the_bumblezone:the_bumblezone,minecraft:overworld)\n" +
                "DIMENSIONALCLEANSE=notadimension\n" +
                "#Disables hostile mob attack based infections in cleansed dimensions\n" +
                "HOSTILEINFECTIONINCLEANSE=true\n" +
                "#Disables player attack based infections in cleansed dimensions\n" +
                "PLAYERINFECTIONCLEANSE=false\n" +
                "#Disables raw food infections in cleansed dimensions\n" +
                "RAWFOODINFECTIONINCLEANSE=true\n" +
                "#Enables the voice chat module, which disabled voice chat when text chat is disabled via infection.\n" +
                "VOICECHATSUPPORT=false\n";
    }
}
