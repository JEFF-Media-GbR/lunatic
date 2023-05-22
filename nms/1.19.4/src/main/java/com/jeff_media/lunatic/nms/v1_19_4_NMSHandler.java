package com.jeff_media.lunatic.nms;

import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class v1_19_4_NMSHandler implements LunaticNMSHandler {

    private final DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();

    @SuppressWarnings("closeable/finalizer")
    public final ServerLevel toNms(final World world) {
        return ((CraftWorld) world).getHandle();
    }

    public ServerPlayer toNms(final Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    @Override
    public void sendTotemAnimation(
            @NotNull
            Player player) {
        ServerPlayer nmsPlayer = toNms(player);
        nmsPlayer.connection.send(new ClientboundEntityEventPacket(nmsPlayer, (byte) 35));
    }

    @Override
    public @NotNull String getDefaultWorldName() {
        return dedicatedServer.getProperties().levelName;
    }

    @Override
    public void setFullTimeWithoutTimeSkipEvent(
            @NotNull
            final World world, final long time, final boolean notifyPlayers) {
        final ServerLevel level = toNms(world);
        level.setDayTime(time);
        if (notifyPlayers) {
            for (final Player player : world.getPlayers()) {
                final ServerPlayer serverPlayer = toNms(player);
                serverPlayer.connection.send(new ClientboundSetTimePacket(level.getGameTime(),
                        serverPlayer.getPlayerTime(),
                        level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
            }
        }
    }

    @Override
    public boolean isServerRunning() {
        return dedicatedServer.isRunning();
    }

    @Override
    public double[] getTPS() {
        return dedicatedServer.recentTps;
    }
}
