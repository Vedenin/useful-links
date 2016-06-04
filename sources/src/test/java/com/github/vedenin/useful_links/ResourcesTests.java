package com.github.vedenin.useful_links;

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
        assertEquals(35, list.size());
        assertEquals("contributing", list.get(10));
    }

    @Test
    public void testGetPopularLanguages() {
        Resources resources = new Resources();
        Map<String, String> map = resources.getPopularLanguages();
        assertEquals(43, map.size());
        assertEquals("Java", map.get("Java"));
    }
}
