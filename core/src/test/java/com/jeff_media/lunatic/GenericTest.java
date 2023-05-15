package com.jeff_media.lunatic;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class GenericTest {

    private ServerMock server;
    private Plugin plugin;

    public Plugin getPlugin() {
        return plugin;
    }

    public Server getServer() {
        return server;
    }

    @BeforeEach
    public void setUp()
    {
        server = MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin("LunaticTest");
    }

    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

}
