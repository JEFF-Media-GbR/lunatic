package com.jeff_media.lunatic.utils;

import com.jeff_media.lunatic.Lunatic;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Player-related utilities
 */
public class PlayerUtils {

    /**
     * Plays the totem of undying animation to a given player. This is the same as <pre>playTotemAnimation(player, null)</pre>
     * Unlike {@link Player#playEffect(EntityEffect)}, this will only be shown to the affected player.
     *
     * @param player Player to play the animation to
     */
    public static void playTotemAnimation(@NotNull final Player player) {
        playTotemAnimation(player, null);
    }

    /**
     * Plays the totem of undying animation to a given player using the provided custom model data.
     * Unlike {@link Player#playEffect(EntityEffect)}, this will only be shown to the affected player.
     *
     * @param player          Player to play the animation to
     * @param customModelData Custom model data to use, or null to not use any custom model data
     */
    public static void playTotemAnimation(@NotNull final Player player, @Nullable final Integer customModelData) {
        final ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        final ItemMeta meta = totem.getItemMeta();
        Objects.requireNonNull(meta).setCustomModelData(customModelData);
        totem.setItemMeta(meta);
        final ItemStack hand = player.getInventory().getItemInMainHand();
        player.getInventory().setItemInMainHand(totem);
        Lunatic.getNmsHandler().sendTotemAnimation(player);
        player.getInventory().setItemInMainHand(hand);
    }
}
