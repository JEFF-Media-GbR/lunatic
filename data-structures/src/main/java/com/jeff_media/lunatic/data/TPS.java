package com.jeff_media.lunatic.data;

/**
 * Represents the server's last TPS
 */
public record TPS(double last1Minute, double last5Minute, double last15Minute) {

    public static TPS of(double[] tps) {
        if (tps.length != 3) {
            throw new IllegalArgumentException(
                    "TPS array doesn't contain 3 values but " + tps.length + ": " + java.util.Arrays.toString(tps));
        }
        return new TPS(tps[0], tps[1], tps[2]);
    }

}
