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

package com.jeff_media.lunatic;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * Represents a configuration that automatically loads the default values from the plugin's resources, if it exists.
 * If no matching resource is found, the default configuration is empty. Automatically creates the file if it doesn't
 * exist, otherwise it loads the already saved file.
 */
public class LunaticConfig extends YamlConfiguration {

    private final Plugin plugin;
    private final String filename;
    private final File file;

    /**
     * Creates a new config with the given filename.
     *
     * @param filename filename of the config
     */
    public LunaticConfig(
            @NotNull
            String filename) {
        this(Lunatic.getPlugin(), Lunatic.getPlugin().getDataFolder(), filename);
    }

    /**
     * Creates a new config with the given filename in the given folder
     *
     * @param folder   folder to save the config in
     * @param filename filename of the config
     */
    public LunaticConfig(
            @NotNull
            File folder,
            @NotNull
            String filename) {
        this(Lunatic.getPlugin(), folder, filename);
    }

    /**
     * Creates a new config with the given filename in the given folder, using the given plugin
     *
     * @param plugin   plugin to use
     * @param folder   folder to save the config in
     * @param filename filename of the config
     */
    public LunaticConfig(
            @NotNull
            Plugin plugin,
            @NotNull
            File folder,
            @NotNull
            final String filename) {
        this.plugin = plugin;
        this.filename = filename;
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new UncheckedIOException(new IOException("Could not create folder " + folder.getAbsolutePath()));
            }
        }
        if (!folder.isDirectory()) {
            throw new IllegalStateException(folder.getAbsolutePath() + " is not a directory.");
        }
        file = new File(folder, filename);
        loadDefaults();
        reload();
    }

    private void loadDefaults() {
        final YamlConfiguration defaultConfig = new YamlConfiguration();

        try (final InputStream inputStream = plugin.getResource(filename)) {
            if (inputStream != null) {
                try (final Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
                    defaultConfig.load(reader);
                }
            }
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Could not load included config file " + filename, exception);
        } catch (final InvalidConfigurationException exception) {
            throw new IllegalArgumentException("Invalid default config for " + filename, exception);
        }

        setDefaults(defaultConfig);
    }

    /**
     * Reloads the configuration
     */
    public void reload() {
        saveDefaultConfig();
        try {
            load(file);
        } catch (final IOException exception) {
            new IllegalArgumentException("Could not find or load file " + filename, exception).printStackTrace();
        } catch (final InvalidConfigurationException exception) {
            plugin.getLogger()
                    .severe("Your config file " + filename +
                            " is invalid, using default values now. Please fix the below mentioned errors and try again:");
            exception.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new UncheckedIOException(new IOException(
                        "Could not create directory " + parent.getAbsolutePath()));

            }
            plugin.saveResource(filename, false);
        }
    }

    /**
     * Saves the configuration under its original file name
     *
     * @throws IOException if the underlying YamlConfiguration throws it
     */
    public void save() throws IOException {
        this.save(file);
    }

}
