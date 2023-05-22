package com.jeff_media.lunatic.internal;

import com.jeff_media.lunatic.data.McVersion;
import com.jeff_media.lunatic.utils.ReflectionUtils;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.Objects;

/**
 * Factory for {@link ServerListPingEvent}, which is required for versions using chat preview
 */
public class ServerListPingEventFactory {

    private static final ServerListPingEventConstructorInvoker CONSTRUCTOR_INVOKER;

    static {
        try {
            if (McVersion.current().isAtLeast(McVersion.v1_19_3)) {
                CONSTRUCTOR_INVOKER = new v1_19_3_Invoker();
            }
            else if (McVersion.current() == McVersion.v1_19_2) {
                CONSTRUCTOR_INVOKER = new v1_19_2_Invoker();
            }
            else if (McVersion.current().isAtLeast(McVersion.v1_19)) {
                CONSTRUCTOR_INVOKER = new v1_19_Invoker();
            }
            else {
                CONSTRUCTOR_INVOKER = new v1_18_Invoker();
            }
        } catch (Exception exception) {
            throw new RuntimeException("Couldn't find any (working) constructor for ServerListPingEvent on version " + McVersion.current(),
                    exception);
        }
    }

    /**
     * Creates a new ServerListPingEvent
     *
     * @param hostname               The hostname
     * @param address                The address
     * @param motd                   The MOTD
     * @param shouldSendChatPreviews Whether chat previews should be sent
     * @param numPlayers             The number of online players
     * @param maxPlayers             The maximum number of players
     * @return The new ServerListPingEvent
     */
    @Contract("_, _, _, _, _, _ -> new")
    public static ServerListPingEvent createServerListPingEvent(String hostname,
                                                                InetAddress address,
                                                                String motd,
                                                                boolean shouldSendChatPreviews,
                                                                int numPlayers,
                                                                int maxPlayers) {
        return CONSTRUCTOR_INVOKER.create(hostname, address, motd, shouldSendChatPreviews, numPlayers, maxPlayers);

    }

    private interface ServerListPingEventConstructorInvoker {
        ServerListPingEvent create(String hostname,
                                   InetAddress address,
                                   String motd,
                                   boolean shouldSendChatPreviews,
                                   int numPlayers,
                                   int maxPlayers);
    }

    /**
     * Constructor invoker for 1.19.3 and 1.19.4
     */
    private static final class v1_19_3_Invoker implements ServerListPingEventConstructorInvoker {

        @Override
        public ServerListPingEvent create(String hostname,
                                          InetAddress address,
                                          String motd,
                                          boolean shouldSendChatPreviews,
                                          int numPlayers,
                                          int maxPlayers) {
            return new ServerListPingEvent(hostname, address, motd, numPlayers, maxPlayers);
        }
    }

    /**
     * Constructor invoker for 1.19.2
     */
    private static final class v1_19_2_Invoker implements ServerListPingEventConstructorInvoker {

        private final Constructor<ServerListPingEvent> constructor =
                Objects.requireNonNull(ReflectionUtils.getConstructor(ServerListPingEvent.class,
                        String.class,
                        InetAddress.class,
                        String.class,
                        boolean.class,
                        int.class,
                        int.class));

        @Override
        public ServerListPingEvent create(String hostname,
                                          InetAddress address,
                                          String motd,
                                          boolean shouldSendChatPreviews,
                                          int numPlayers,
                                          int maxPlayers) {
            try {
                return constructor.newInstance(hostname, address, motd, shouldSendChatPreviews, numPlayers, maxPlayers);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Constructor invoker for 1.19 and 1.19.1
     */
    private static final class v1_19_Invoker implements ServerListPingEventConstructorInvoker {

        private final Constructor<ServerListPingEvent> constructor =
                Objects.requireNonNull(ReflectionUtils.getConstructor(ServerListPingEvent.class,
                        InetAddress.class,
                        String.class,
                        boolean.class,
                        int.class,
                        int.class));

        @Override
        public ServerListPingEvent create(String hostname,
                                          InetAddress address,
                                          String motd,
                                          boolean shouldSendChatPreviews,
                                          int numPlayers,
                                          int maxPlayers) {
            try {
                return constructor.newInstance(address, motd, shouldSendChatPreviews, numPlayers, maxPlayers);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Constructof for 1.18, 1.18.1 and 1.18.2
     */
    private static final class v1_18_Invoker implements ServerListPingEventConstructorInvoker {

        private final Constructor<ServerListPingEvent> constructor =
                Objects.requireNonNull(ReflectionUtils.getConstructor(ServerListPingEvent.class,
                        InetAddress.class,
                        String.class,
                        int.class,
                        int.class));

        @Override
        public ServerListPingEvent create(String hostname,
                                          InetAddress address,
                                          String motd,
                                          boolean shouldSendChatPreviews,
                                          int numPlayers,
                                          int maxPlayers) {
            try {
                return constructor.newInstance(address, motd, numPlayers, maxPlayers);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

