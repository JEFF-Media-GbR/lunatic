package com.jeff_media.lunatic;

import com.jeff_media.lunatic.data.McVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class McVersionTest extends GenericTest {

    @Test
    public void testIdentityMcVersion() {
        Assertions.assertSame(McVersion.v1_18, McVersion.of(1, 18));
        Assertions.assertSame(McVersion.of(1, 18, 1), McVersion.of(1, 18, 1));
    }

    @Test
    public void testIsAtLeast() {

        final McVersion v1_8_8 = McVersion.of(1, 8, 8);
        final McVersion v1_17 = McVersion.of(1, 17);
        final McVersion v1_18 = McVersion.of(1, 18);
        final McVersion v1_18_1 = McVersion.of(1, 18, 1);
        final McVersion v1_18_2 = McVersion.of(1, 18, 2);
        final McVersion v1_19 = McVersion.of(1, 19);

        Assertions.assertEquals(v1_8_8.toString(), "1.8.8");
        Assertions.assertEquals(v1_19.toString(), "1.19");

        Assertions.assertTrue(v1_18.isAtLeast(v1_18));
        Assertions.assertFalse(v1_18.isAtLeast(v1_18_1));
        Assertions.assertTrue(v1_18.isAtLeast(v1_17));

        Assertions.assertEquals(v1_18_2, McVersion.of(1, 18, 2));

        //Assertions.assertEquals(McVersion.current(), v1_18);
    }

    @Test
    public void testComparable() {
        final McVersion v1_8_8 = McVersion.of(1, 8, 8);
        final McVersion v1_18 = McVersion.of(1, 18);
        final McVersion v1_18_2 = McVersion.of(1, 18, 2);
        final McVersion v1_19 = McVersion.of(1, 19);

        final List<McVersion> unsorted = Arrays.asList(v1_18, v1_8_8, v1_19, v1_18_2);
        final McVersion[] sorted = new ArrayList<>(unsorted).stream().sorted().toArray(McVersion[]::new);
        Assertions.assertArrayEquals(sorted, new McVersion[]{v1_8_8, v1_18, v1_18_2, v1_19});
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("1.19.1", McVersion.of(1, 19, 1).toString());
        Assertions.assertEquals("1.17", McVersion.of(1, 17).toString());
        Assertions.assertEquals("1.17", McVersion.of(1, 17, 0).getName());
    }

}
