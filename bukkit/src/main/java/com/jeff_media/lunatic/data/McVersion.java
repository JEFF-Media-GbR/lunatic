package com.jeff_media.lunatic.data;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a Minecraft version. Can be compared to other versions, where the natural order is "lower" versions first.
 * The instances returned by {@link McVersion#of(int, int, int)} and {@link McVersion#current()} are cached, hence it
 * is safe
 * to use
 * {@code ==} to
 * compare them.
 */
@SuppressWarnings("unused")
public class McVersion implements Comparable<McVersion> {

    /**
     * The version of the currently running server
     */
    public static final McVersion CURRENT_VERSION;
    private static final HashMap<Key, McVersion> VERSIONS = new HashMap<>();
    public static final McVersion v1_18 = McVersion.of(1, 18);
    public static final McVersion v1_18_1 = McVersion.of(1, 18, 1);
    public static final McVersion v1_18_2 = McVersion.of(1, 18, 2);
    public static final McVersion v1_19 = McVersion.of(1, 19);
    public static final McVersion v1_19_1 = McVersion.of(1, 19, 1);
    public static final McVersion v1_19_2 = McVersion.of(1, 19, 2);
    public static final McVersion v1_19_3 = McVersion.of(1, 19, 3);
    public static final McVersion v1_19_4 = McVersion.of(1, 19, 4);

    static {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        final int currentMajor = Integer.parseInt(split[0]);
        final int currentMinor = Integer.parseInt(split[1]);
        boolean hasPatch = split.length == 3;
        final int currentPatch = hasPatch ? Integer.parseInt(split[2]) : 0;

        CURRENT_VERSION = McVersion.of(currentMajor, currentMinor, currentPatch);
    }

    private final int major;
    private final int minor;
    private final int patch;

    private McVersion(final int major, final int minor) {
        this(major, minor, 0);
    }

    private McVersion(final int major, final int minor, final int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        if (VERSIONS.containsKey(new Key(major, minor, patch))) {
            throw new IllegalArgumentException("McVersion " + getName() + " already exists in cache.");
        }
    }

    /**
     * Get an McVersion from a string. The returned versin is cached, so it is safe to use {@code ==} to compare them.
     *
     * @param major Major version, e.g. 1 for 1.19
     * @param minor Minor version e.g. 19 for 1.19
     */
    public static McVersion of(int major, int minor) {
        return of(major, minor, 0);
    }

    /**
     * Get an McVersion from a string. The returned versin is cached, so it is safe to use {@code ==} to compare them.
     *
     * @param major Major version, e.g. 1 for 1.19.4
     * @param minor Minor version e.g. 19 for 1.19.4
     * @param patch Patch version e.g. 4 for 1.19.4
     */
    public static McVersion of(int major, int minor, int patch) {
        return VERSIONS.computeIfAbsent(new Key(major, minor, patch), ints -> new McVersion(major, minor, patch));
    }

    /**
     * Get the version of the currently running server. The returned version is cached, so it is safe to use {@code ==
     * } to
     * compare it.
     */
    public static McVersion current() {
        return CURRENT_VERSION;
    }

    /**
     * Checks if this version is at least as new as the given version. For example, 1.16.5 is at least as new as 1.16.4
     * and 1.16.3, but not as new as 1.17.
     *
     * @param other The version to compare to
     * @return True if this version is at least as new as the given version
     */
    public boolean isAtLeast(final McVersion other) {
        return this.compareTo(other) >= 0;
    }

    /**
     * Compares this McVersion with the specified McVersion for order. Returns a negative integer, zero, or a positive
     * integer as this McVersion is older than, equal to, or newer than the specified McVersion.
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this McVersion is older than, equal to, or newer than
     * the specified McVersion.
     */
    @Override
    public int compareTo(
            @NotNull
            final McVersion other) {
        if (this.major > other.major) {
            return 3;
        }
        if (other.major > this.major) {
            return -3;
        }
        if (this.minor > other.minor) {
            return 2;
        }
        if (other.minor > this.minor) {
            return -2;
        }
        return Integer.compare(this.patch, other.patch);
    }

    /**
     * Gets the "major" part of this McVersion. For 1.16.5, this would be 1
     *
     * @return The major part of this McVersion
     */
    public int getMajor() {
        return major;
    }

    /**
     * Gets the "minor" part of this McVersion. For 1.16.5, this would be 16
     *
     * @return The minor part of this McVersion
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Gets the "patch" part of this McVersion, or 0 if there is none. For 1.16.5, this would be 5. For 1.16, this
     * would be 0.
     *
     * @return The patch part of this McVersion, or 0 if there is none
     */
    public int getPatch() {
        return patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final McVersion mcVersion = (McVersion) o;
        return major == mcVersion.major && minor == mcVersion.minor && patch == mcVersion.patch;
    }

    /**
     * Same as {@link #getName()}
     *
     * @return The name of this McVersion
     * @see #getName()
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Gets the name of this McVersion. For 1.16.5, this would be "1.16.5". For 1.16, this would be "1.16".
     *
     * @return The name of this McVersion
     */
    public String getName() {
        if (patch == 0) {
            return major + "." + minor;
        }
        else {
            return major + "." + minor + "." + patch;
        }
    }

    /**
     * Checks if this version is at least as new as the given version.
     *
     * @param major The major version to compare to
     * @param minor The minor version to compare to
     * @param patch The patch version to compare to
     * @see #isAtLeast(McVersion)
     * @return True if this version is at least as new as the given version
     */
    public boolean isAtLeast(final int major, final int minor, final int patch) {
        return this.isAtLeast(McVersion.of(major, minor, patch));
    }

    /**
     * Checks if this version is at least as new as the given version.
     *
     * @param major The major version to compare to
     * @param minor The minor version to compare to
     * @see #isAtLeast(McVersion)
     * @return True if this version is at least as new as the given version
     */
    public boolean isAtLeast(final int major, final int minor) {
        return this.isAtLeast(McVersion.of(major, minor));
    }

    private record Key(int major, int minor, int patch) {

    }

}
