package com.github.vedenin.useful_links;

import org.junit.Test;

import static com.github.vedenin.useful_links.Constants.LANGUAGES;
import static com.github.vedenin.useful_links.Constants.POPULAR_LANGUAGES;
import static org.junit.Assert.assertEquals;

/**
 * Created by vvedenin on 6/3/2016.
 */
public class ConstantTests {
    @Test
    public void testLanguages() {
        assertEquals(408, LANGUAGES.size());
    }

    @Test
    public void testPopularLanguages() {
        assertEquals(43, POPULAR_LANGUAGES.size());
    }

}
