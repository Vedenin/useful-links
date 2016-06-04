package com.github.vedenin.useful_links;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

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
    }
}
