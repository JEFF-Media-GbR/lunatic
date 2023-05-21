package com.jeff_media.lunatic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LunaticMainTest extends GenericTest {

    @Test
    public void testInit() {
        Assertions.assertThrows(IllegalStateException.class, Lunatic::getPlugin);
        Lunatic.init(getPlugin());
        Assertions.assertEquals(getPlugin(), Lunatic.getPlugin());
    }
}
