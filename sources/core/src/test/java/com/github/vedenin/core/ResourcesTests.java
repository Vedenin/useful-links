package com.github.vedenin.core;

import com.github.vedenin.core.resources.Resources;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test Resources class
 *
 * Created by vedenin on 04.06.16.
 */
public class ResourcesTests {

    @Test
    public void testGetNonProjectHeaders() {
        Resources resources = new Resources();
        List<String> list = resources.getNonProjectHeaders();
        assertEquals(41, list.size());
        assertEquals("resources", list.get(10).trim());
    }

    @Test
    public void testGetNonProjectMainHeaders() {
        Resources resources = new Resources();
        List<String> list = resources.getNonProjectMainHeaders();
        assertEquals(1, list.size());
        assertEquals("user groups", list.get(0).trim());
    }


    @Test
    public void testGetPopularLanguages() {
        Resources resources = new Resources();
        Map<String, String> map = resources.getPopularLanguages();
        assertEquals(43, map.size());
        assertEquals("Java", map.get("Java").trim());
    }

    @Test
    public void testGetProgrammingLanguages() {
        Resources resources = new Resources();
        Map<String, String> map = resources.getProgrammingLanguages();
        assertEquals(408, map.size());
        assertEquals("Java", map.get("Java").trim());
    }
}
