package com.github.vedenin.useful_links.core.common.containers;

import com.github.vedenin.useful_links.core.common.annotations.PropertiesContainer;

/**
 *  Word - Category mapping
 *
 * Created by vvedenin on 5/14/2016.
 */
@PropertiesContainer // class without getter and setter (see Properties in C#)
public class WordCategoryContainer {
    public String category;
    public Integer number;
    public String word;

    public static WordCategoryContainer create() {
        return new WordCategoryContainer();
    }

    @Override
    public String toString() {
        return "{" +
                "category='" + category + '\'' +
                ", number=" + number +
                ", word='" + word + '\'' +
                '}';
    }
}
