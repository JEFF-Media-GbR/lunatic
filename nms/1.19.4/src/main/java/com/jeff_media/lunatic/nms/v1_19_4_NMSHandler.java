package com.jeff_media.lunatic.nms;

import com.jeff_media.lunatic.nms.LunaticNMSHandler;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_19_4_NMSHandler implements LunaticNMSHandler {
    @Override
    public void sendTotemAnimation(Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
        nmsPlayer.connection.send(new ClientboundEntityEventPacket(nmsPlayer, (byte) 35));
    }
}
