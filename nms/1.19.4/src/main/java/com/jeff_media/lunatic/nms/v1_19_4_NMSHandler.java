package com.jeff_media.lunatic.nms;

import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
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
    @Override
    public void sendTotemAnimation(@NotNull Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
        nmsPlayer.connection.send(new ClientboundEntityEventPacket(nmsPlayer, (byte) 35));
    }

    @Override
    public @NotNull String getDefaultWorldName() {
        return ((CraftServer) Bukkit.getServer()).getServer().getProperties().levelName;
    }

    @Override
    public void setFullTimeWithoutTimeSkipEvent(@NotNull final World world, final long time, final boolean notifyPlayers) {
        final ServerLevel level = ((CraftWorld) world).getHandle();
        level.setDayTime(time);
        if (notifyPlayers) {
            for (final Player player : world.getPlayers()) {
                final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
                serverPlayer.connection.send(new ClientboundSetTimePacket(serverPlayer.level.getGameTime(), serverPlayer.getPlayerTime(), serverPlayer.level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
            }
        }
    }
}
