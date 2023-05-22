package com.jeff_media.lunatic.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when Lunatic does not support NMS features for the current Minecraft version.
 */
public class NMSNotSupportedException extends Exception {

    public NMSNotSupportedException(
            @NotNull
            String version) {
        super("This version of Lunatic does not support NMS features for Minecraft version " + version + ".");
    }

}
