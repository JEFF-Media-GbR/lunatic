package com.jeff_media.lunatic.nms;


import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_16_5_NMSHandler implements LunaticNMSHandler {
    @Override
    public void sendTotemAnimation(Player player) {
        EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(nmsPlayer, (byte) 35));
    }
}
