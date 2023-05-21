package com.jeff_media.lunatic.tests;

import com.jeff_media.lunatic.utils.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayUtilsTest {

    @Test
    public void testCombine() {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String[] ab = new String[]{a, b};
        String[] cd = new String[]{c, d};
        String[] abcd = new String[]{a, b, c, d};
        Assertions.assertArrayEquals(abcd, ArrayUtils.combine(ab, cd));
    }

    @Test
    public void testRemoveAtIndex() {
        Character[] test = new Character[] {'t','e','s','t'};
        Character[] tet = new Character[] {'t','e','t'};
        Assertions.assertArrayEquals(tet, ArrayUtils.removeAtIndex(test, 1));
    }

    @Test
    public void testAddAfter() {
        Character[] test = new Character[] {'t','e','s','t'};
        Character[] test2 = new Character[] {'t','e','s','t','2'};
        Assertions.assertArrayEquals(test2, ArrayUtils.addAfter(test, '2'));
    }
}
