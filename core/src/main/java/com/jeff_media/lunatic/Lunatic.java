package com.jeff_media.lunatic;

import org.bukkit.plugin.Plugin;

/**
 * Main class for Lunatic
 */
public class Lunatic {

    private final Plugin plugin;

    /**
     * Create a new Lunatic instance
     * @param plugin Plugin instance
     */
    public Lunatic(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the plugin instance
     * @return Plugin instance
     */
    public Plugin getPlugin() {
        return plugin;
    }
}
