package com.jeff_media.lunatic.tests;

import com.jeff_media.lunatic.utils.EnumUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumUtilsTest {

    @Test
    void testGetIfPresent() {
        Assertions.assertSame(EnumUtils.getIfPresent(TestEnum.class, "A").orElse(null), TestEnum.A);
    }

    @Test
    void testGetEnumsFromList() {
        List<String> wrongArgs = new ArrayList<>();
        final List<TestEnum> materials = EnumUtils.getEnumsFromList(TestEnum.class,
                Arrays.asList("A", "B", "C", "D"),
                Collectors.toList(),
                wrongArgs::add);
        Assertions.assertEquals(3, materials.size());
        Assertions.assertSame(materials.get(0), TestEnum.A);
        Assertions.assertSame(materials.get(1), TestEnum.B);
        Assertions.assertSame(materials.get(2), TestEnum.C);
        Assertions.assertEquals(1, wrongArgs.size());
        Assertions.assertEquals("D", wrongArgs.get(0));
    }

    @Test
    void testNextEnum() {
        Assertions.assertSame(TestEnum.B, EnumUtils.getNextElement(TestEnum.A, true));
        Assertions.assertSame(TestEnum.C, EnumUtils.getNextElement(TestEnum.B, true));
        Assertions.assertSame(TestEnum.A, EnumUtils.getNextElement(TestEnum.C, true));
        Assertions.assertNull(EnumUtils.getNextElement(TestEnum.C, false));
    }

    @Test
    void testGetEnumsFromRegexList() {
        List<String> regexes = Arrays.asList("RED_.*", ".*_WOOL", ".*_CONCRETE$", "^DIRT$");
        List<FakeMaterial> materials =
                EnumUtils.getEnumsFromRegexList(FakeMaterial.class, regexes, Collectors.toList());
        Assertions.assertEquals(7, materials.size());
        Assertions.assertTrue(materials.contains(FakeMaterial.RED_WOOL));
        Assertions.assertTrue(materials.contains(FakeMaterial.BLUE_WOOL));
        Assertions.assertTrue(materials.contains(FakeMaterial.GREEN_WOOL));
        Assertions.assertTrue(materials.contains(FakeMaterial.RED_CONCRETE));
        Assertions.assertTrue(materials.contains(FakeMaterial.BLUE_CONCRETE));
        Assertions.assertTrue(materials.contains(FakeMaterial.GREEN_CONCRETE));
        Assertions.assertTrue(materials.contains(FakeMaterial.DIRT));
        Assertions.assertFalse(materials.contains(FakeMaterial.DIRT_BLOCK));
    }

    private enum TestEnum {
        A,
        B,
        C
    }

    private enum FakeMaterial {
        RED_WOOL,
        BLUE_WOOL,
        GREEN_WOOL,
        RED_CONCRETE,
        BLUE_CONCRETE,
        GREEN_CONCRETE,
        DIRT,
        DIRT_BLOCK,
        GRASS,
        STONE;
    }

}
