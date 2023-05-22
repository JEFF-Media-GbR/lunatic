package com.jeff_media.lunatic.data;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * Provides version comparing methods
 */
@SuppressWarnings("unused")
public class McVersion implements Comparable<McVersion> {

    private static final HashMap<Key, McVersion> VERSIONS = new HashMap<>();
    public static final McVersion v1_18 = McVersion.of(1, 18);
    public static final McVersion v1_18_1 = McVersion.of(1, 18, 1);
    public static final McVersion v1_18_2 = McVersion.of(1, 18, 2);
    public static final McVersion v1_19 = McVersion.of(1, 19);
    public static final McVersion v1_19_1 = McVersion.of(1, 19, 1);
    public static final McVersion v1_19_2 = McVersion.of(1, 19, 2);
    public static final McVersion v1_19_3 = McVersion.of(1, 19, 3);
    public static final McVersion v1_19_4 = McVersion.of(1, 19, 4);
    private static final McVersion CURRENT_VERSION;

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
        //VERSIONS.put(new int[]{major, minor, patch}, this);
    }

    public static McVersion of(int major, int minor) {
        return of(major, minor, 0);
    }

    public static McVersion of(int major, int minor, int patch) {
        return VERSIONS.computeIfAbsent(new Key(major, minor, patch), ints -> new McVersion(major, minor, patch));
    }

    /**
     * Gets the currently running McVersion
     */
    public static McVersion current() {
        return CURRENT_VERSION;
    }

    public boolean isAtLeast(final McVersion other) {
        return this.compareTo(other) >= 0;
    }

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
     */
    public int getMajor() {
        return major;
    }

    /**
     * Gets the "minor" part of this McVersion. For 1.16.5, this would be 16
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Gets the "patch" part of this McVersion. For 1.16.5, this would be 5.
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

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        if (patch == 0) {
            return major + "." + minor;
        }
        else {
            return major + "." + minor + "." + patch;
        }
    }

    public boolean isAtLeast(final int major, final int minor, final int patch) {
        return this.isAtLeast(McVersion.of(major, minor, patch));
    }

    public boolean isAtLeast(final int major, final int minor) {
        return this.isAtLeast(McVersion.of(major, minor));
    }

    private record Key(int major, int minor, int patch) {

    }

}
