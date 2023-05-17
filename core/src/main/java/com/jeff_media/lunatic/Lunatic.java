package com.jeff_media.lunatic;

import com.jeff_media.lunatic.exceptions.NMSNotSupportedException;
import com.jeff_media.lunatic.nms.LunaticNMSHandler;
import com.jeff_media.lunatic.nms.v1_19_4_NMSHandler;
import org.bukkit.plugin.Plugin;

/**
 * Main class for Lunatic
 */
public class Lunatic {

    private static Plugin plugin;
    private static LunaticNMSHandler nmsHandler;

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
        ensureInit();
        return plugin;
    }

    /**
     * Enables NMS features
     * @throws NMSNotSupportedException
     */
    public static void enableNmsFeatures() throws NMSNotSupportedException {
        ensureInit();
        nmsHandler = createNmsHandler();
    }

    public static LunaticNMSHandler getNmsHandler() {
        if(nmsHandler == null) {
            throw new IllegalStateException("NMS features are not enabled yet. Call Lunatic#enableNmsFeatures() first.");
        }
        return nmsHandler;
    }

    private static LunaticNMSHandler createNmsHandler() throws NMSNotSupportedException {
        String version = getPlugin().getServer().getBukkitVersion().split("-")[0];
        switch (version) {
            case "1.19.4":
                return new v1_19_4_NMSHandler();
                // ...
            default:
                throw new NMSNotSupportedException(version);
        }
    }

    private static void ensureInit() {
        if(plugin == null) {
            throw new IllegalStateException("Lunatic is not initialized yet. Call Lunatic#init(Plugin) first.");
        }
    }

}
