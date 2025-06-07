package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.config.ImmortuosConfigData;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonImmortuos {

    public static ImmortuosConfigData configData = null;

    private static HashMap<String, ImmortuosEffectMath> GENES = new HashMap<>();

    private static HashMap<String, ImmortuosEffectMath> ACTIVEGENES = new HashMap<>();

    private static HashMap<String, ImmortuosEffectMath> CONTAMINATIONS = new HashMap<>();

    private static HashMap<String, ImmortuosEffectMath> ACTIVECONTAMINATIONS = new HashMap<>();

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        loadConfig();
    }
    
    public static void loadConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            JsonReader reader = new JsonReader(new FileReader("config/immortuosCalyx.json"));
            configData = gson.fromJson(reader, ImmortuosConfigData.class);
        } catch (FileNotFoundException e) {
            try (Writer writer = new FileWriter("config/immortuosCalyx.json")) {
                writer.flush();
                gson.toJson(ImmortuosConfigData.buildDefaultConfig(), writer);
                configData = ImmortuosConfigData.buildDefaultConfig();
            } catch (IOException ex) {
                Constants.LOG.error("ImmortuosCalyx: Failed to write default data!");
                throw new RuntimeException(ex);
            }
        }

        HashMap<String, ImmortuosEffectMath> loadedGenes = new HashMap<>(GENES);
        HashMap<String, ImmortuosEffectMath> loadedContaminations = new HashMap<>(CONTAMINATIONS);
        //Parse configed out items here,
        for (String disabledGene : getDisabledGenes()) {
            loadedGenes.remove(disabledGene);
        }

        for (String disabledContamination : getDisabledContamination()) {
            loadedContaminations.remove(disabledContamination);
        }

        setActiveGenes(loadedGenes);
        setActiveContaminations(loadedContaminations);
    }

    public static void bootStrapGenes() {
        GENES.put("gene_zombie", new ImmortuosEffectMath(Services.PLATFORM.GENE_ZOMBIE(), 20));
        GENES.put("gene_ocelot", new ImmortuosEffectMath(Services.PLATFORM.GENE_OCELOT(), 4));
        GENES.put("gene_turtle", new ImmortuosEffectMath(Services.PLATFORM.GENE_TURTLE(), 10));
        GENES.put("gene_iron_golem", new ImmortuosEffectMath(Services.PLATFORM.GENE_IRON_GOLEM(), 15));
        GENES.put("gene_frog", new ImmortuosEffectMath(Services.PLATFORM.GENE_FROG(), 10));
        GENES.put("gene_silverfish", new ImmortuosEffectMath(Services.PLATFORM.GENE_SILVERFISH()));
        GENES.put("gene_enderman", new ImmortuosEffectMath(Services.PLATFORM.GENE_ENDERMAN()));
        GENES.put("gene_vindicator", new ImmortuosEffectMath(Services.PLATFORM.GENE_VINDICATOR()));
        GENES.put("gene_wither_skeleton", new ImmortuosEffectMath(Services.PLATFORM.GENE_WITHER_SKELETON()));
        GENES.put("gene_magma_cube", new ImmortuosEffectMath(Services.PLATFORM.GENE_MAGMA_CUBE(), 2));
        GENES.put("gene_immortuos", new ImmortuosEffectMath(Services.PLATFORM.GENE_IMMORTUOS()));

        CONTAMINATIONS.put("contamination_hydrophobia", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_HYDROPHOBIA()));
        CONTAMINATIONS.put("contamination_genetic_destablization", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_GENETIC_DESTABILIZATION(), 10));
        CONTAMINATIONS.put("contamination_stagger", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_STAGGER(), 2));
        CONTAMINATIONS.put("contamination_knee_pastafication", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_KNEE_PASTAFICATION(), 20));
        CONTAMINATIONS.put("contamination_giant", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_GIANT()));
        CONTAMINATIONS.put("contamination_glass", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_GLASS()));
        CONTAMINATIONS.put("contamination_shady", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_SHADY(), 20));
        CONTAMINATIONS.put("contamination_heliophobia", new ImmortuosEffectMath(Services.PLATFORM.CONTAMINATION_HELIOPHOBIA()));

        HashMap<String, ImmortuosEffectMath> loadedGenes = new HashMap<>(GENES);
        HashMap<String, ImmortuosEffectMath> loadedContaminations = new HashMap<>(CONTAMINATIONS);
        //Parse configed out items here,
        for (String disabledGene : getDisabledGenes()) {
            loadedGenes.remove(disabledGene);
        }

        for (String disabledContamination : getDisabledContamination()) {
            loadedContaminations.remove(disabledContamination);
        }

        setActiveGenes(loadedGenes);
        setActiveContaminations(loadedContaminations);
    }

    private static ArrayList<String> getDisabledGenes() {
        if (configData.getDisabledGenes() == null) return new ArrayList<>();
        return new ArrayList<>(configData.getDisabledGenes());
    }

    private static ArrayList<String> getDisabledContamination() {
        if (configData.getDisabledContamination() == null) return new ArrayList<>();
        return new ArrayList<>(configData.getDisabledContamination());
    }


    public static void addGenes(HashMap<String, ImmortuosEffectMath> genes) {
        CommonImmortuos.GENES.putAll(genes);
    }

    public static void addContaminations(HashMap<String, ImmortuosEffectMath> contaminations) {
        CommonImmortuos.CONTAMINATIONS.putAll(contaminations);
    }

    public static void setActiveGenes(HashMap<String, ImmortuosEffectMath> activeGenes) {
        CommonImmortuos.ACTIVEGENES.clear();
        CommonImmortuos.ACTIVEGENES.putAll(activeGenes);
    }

    public static void setActiveContaminations(HashMap<String, ImmortuosEffectMath> activeContaminations) {
        CommonImmortuos.ACTIVECONTAMINATIONS.clear();
        CommonImmortuos.ACTIVECONTAMINATIONS.putAll(activeContaminations);
    }

    public static HashMap<String, ImmortuosEffectMath> getActiveGenes() {
        return ACTIVEGENES;
    }

    public static HashMap<String, ImmortuosEffectMath> getActiveContaminations() {
        return ACTIVECONTAMINATIONS;
    }

    public static final TagKey<Biome> MushroomBiomes = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "land_spawnable"));
}

