package com.jeff_media.lunatic;

import org.bukkit.plugin.Plugin;

/**
 * Main class for Lunatic
 */
public class Lunatic {

    private static Plugin plugin;

    private Lunatic() {}

    /**
     * Initializes Lunatic
     * @param plugin Plugin instance
     */
    public static void init(Plugin plugin) {
        if(Lunatic.plugin != null) {
            throw new IllegalStateException("Lunatic is already initialized");
        }
        Lunatic.plugin = plugin;
    }

    /**
     * Get the plugin instance
     * @return Plugin instance
     */
    public static Plugin getPlugin() {
        if(plugin == null) {
            throw new IllegalStateException("Lunatic is not initialized yet. Call Lunatic#init(Plugin) first.");
        }
        return plugin;
    }

}
