package com.jeff_media.lunatic.data;

/**
 * Represents the server's current life phase
 */
public enum ServerLifePhase {
    /**
     * Server's starting up (before it says "Done" in console)
     */
    STARTUP,
    /**
     * Server's running (after it says "Done" in console). This will also be the current phase doing a /reload
     */
    RUNNING_OR_RELOADING,
    /**
     * Server's shutting down
     */
    SHUTDOWN,
    /**
     * Couldn't get the server's life phase
     */
    UNKNOWN
}