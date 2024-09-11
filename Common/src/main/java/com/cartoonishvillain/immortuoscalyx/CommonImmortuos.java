package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.config.ImmortuosConfigData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonImmortuos {

    public static ImmortuosConfigData configData = null;

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
    }
}