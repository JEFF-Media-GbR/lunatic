/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.lunatic.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

/**
 * Texr, word and language related methods, like capitalization or getting translation keys
 */
public class TextUtils {

    private static final String EMPTY_STRING = "";

    /**
     * Turns an Enum' name into a nicer name. E.g. DIAMOND_PICKAXE will return "Diamond Pickaxe".
     * This is the same as calling @see #getNiceName(String) with the enum's name.
     *
     * @param someEnum some enum
     * @return Human-readable name
     * @see #getNiceName(String)
     */
    public static String getNiceEnumName(final Enum<?> someEnum) {
        return getNiceName(someEnum.name());
    }

    /**
     * Turns the first letter of a String to uppercase, and all others lowercase. This will e.g. turn "DIAMOND
     * PICKAXE" into "Diamond pickaxe".
     *
     * @param string String to change
     * @return String with first letter uppercase and all others lowercase
     */
    public static String upperCaseFirstLetterOnly(final String string) {
        return upperCaseFirstLetter(string.toLowerCase(Locale.ROOT));
    }

    /**
     * Turns the first letter of a String to uppercase. This will e.g. turn "diamond PICKAXE" into "Diamond PICKAXE".
     *
     * @param word String to change
     * @return String with first letter uppercase
     */
    public static String upperCaseFirstLetter(final @NotNull String word) {
        if (word.length() == 0) {
            return EMPTY_STRING;
        }
        if (word.length() == 1) {
            return word.toUpperCase(Locale.ROOT);
        }
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
    }

    /**
     * Converts a NamespacedKey into a human-readable name, ignoring the namespace. For example,
     * "minecraft:warm_ocean" will return "Warm Ocean". This is the same as calling @see #getNiceName(String) with
     * the key's key.
     *
     * @param key the NamespacedKey to convert
     * @return the human-readable name
     * @see #getNiceName(String)
     */
    public static String getNiceNamespacedKeyName(
            @NotNull
            final NamespacedKey key) {
        return getNiceName(key.getKey());
    }

    /**
     * Converts a given String into a human-readable String, by replacing underscores with spaces, and making all
     * words Uppercase. For example, "ARMOR_STAND" will return "Armor Stand".
     * <p>
     * This is the same as replacing all underscores with spaces, splitting by spaces, calling @see #upperCaseFirstLetterOnly(String) on each word, and then joining them together with spaces.
     *
     * @param string the String to convert
     * @return the human-readable String
     */
    public static String getNiceName(
            @NotNull
            final String string) {
        final StringBuilder builder = new StringBuilder();
        final String[] split = string.split("_");
        final Iterator<String> iterator = Arrays.stream(split).iterator();
        while (iterator.hasNext()) {
            builder.append(upperCaseFirstLetterOnly(iterator.next().toLowerCase(Locale.ROOT)));
            if (iterator.hasNext()) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * Returns the correct English genitive suffix for the given word. E.g., for "mfnalex" it will return "'s", for "Jesus" it will return "'"
     *
     * @param word The word to get the genitive suffix for
     * @return The genitive suffix
     */
    public static String getGenitiveSuffix(final @NotNull String word) {
        final String strippedWord = ChatColor.stripColor(word);
        if (strippedWord.endsWith("s")) {
            return "'";
        }
        return "'s";
    }

}
