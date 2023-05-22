package com.jeff_media.lunatic.utils;

import com.jeff_media.lunatic.Lunatic;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * World related methods
 */
@SuppressWarnings("unused")
public final class WorldUtils {

    /**
     * Utility class
     */
    private WorldUtils() {

    }

    /**
     * Gets the default world
     *
     * @nms
     */
    public static @NotNull World getDefaultWorld() {
        return Objects.requireNonNull(Bukkit.getWorld(getDefaultWorldName()));
    }

    /**
     * Gets the default world name
     *
     * @nms
     */
    public static @NotNull String getDefaultWorldName() {
        return Lunatic.getNmsHandler().getDefaultWorldName();
    }

    /**
     * Sets the full game time for a world without calling {@link org.bukkit.event.world.TimeSkipEvent}
     *
     * @param world         The world
     * @param time          The time
     * @param notifyPlayers Whether to send a time update packet to all players
     * @nms
     */
    public static void setFullTimeWithoutTimeSkipEvent(
            @NotNull
            final World world, final long time, final boolean notifyPlayers) {
        Lunatic.getNmsHandler().setFullTimeWithoutTimeSkipEvent(world, time, notifyPlayers);
    }
}
