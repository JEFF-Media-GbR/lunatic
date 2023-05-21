package com.jeff_media.lunatic.nms;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface LunaticNMSHandler {

    void sendTotemAnimation(@NotNull Player player);

    @NotNull String getDefaultWorldName();

    void setFullTimeWithoutTimeSkipEvent(@NotNull World world, long time, boolean notifyPlayers);
}
