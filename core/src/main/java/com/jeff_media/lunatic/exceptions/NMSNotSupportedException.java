package com.jeff_media.lunatic.exceptions;

public class NMSNotSupportedException extends Exception {

        public NMSNotSupportedException(String version) {
            super("This version of Lunatic does not support NMS features for Minecraft version " + version + ".");
        }

}
