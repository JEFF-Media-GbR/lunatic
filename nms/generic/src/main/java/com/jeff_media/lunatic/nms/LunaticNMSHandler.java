package com.jeff_media.lunatic.nms;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface LunaticNMSHandler {

    /**
     * Plays the totem of undying animation to a given player
     *
     * @param player Player to play the animation to
     */
    void sendTotemAnimation(
            @NotNull
            Player player);

    /**
     * Gets the default world name
     *
     * @return The default world name
     */
    @NotNull
    String getDefaultWorldName();

    /**
     * Sets the full game time for a world without calling {@link org.bukkit.event.world.TimeSkipEvent}
     *
     * @param world         The world
     * @param time          The time
     * @param notifyPlayers Whether to send a time update packet to all players in that world
     */
    void setFullTimeWithoutTimeSkipEvent(
            @NotNull
            World world, long time, boolean notifyPlayers);

    /**
     * Checks whether the server is running
     *
     * @return Whether the server is running
     */
    boolean isServerRunning();

    /**
     * Gets the TPS of the server
     *
     * @return The TPS of the server
     */
    double[] getTPS();
}
