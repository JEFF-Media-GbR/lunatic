package com.jeff_media.lunatic.utils;

import com.jeff_media.lunatic.Lunatic;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * World related methods
 */
public final class WorldUtils {

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
    public static void setFullTimeWithoutTimeSkipEvent(@NotNull final World world, final long time, final boolean notifyPlayers) {
        Lunatic.getNmsHandler().setFullTimeWithoutTimeSkipEvent(world, time, notifyPlayers);
    }

    /**
     * Gets the lowest possible building height for a world. It's the same as {@link World#getMinHeight()} but also works on 1.16.4 and earlier
     */
    public static int getWorldMinHeight(final @NotNull World world) {
        return HAS_WORLD_MIN_HEIGHT_METHOD ? world.getMinHeight() : 0;
    }
}
