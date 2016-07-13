package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.classificator.WordCategoryClassificator;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import com.github.vedenin.useful_links.common.containers.WordCategoryContainer;
import com.github.vedenin.useful_links.crawlers.old.JavaUsefulProjects;
import org.junit.Test;

import java.util.Map;

/**
 * Created by vvedenin on 7/12/2016.
 */
public class WordCategoryClassificatorTest {
    @Test
    public void testWordCategoryClassificator() {
        JavaUsefulProjects crawler = new JavaUsefulProjects();
        WordCategoryClassificator collector = new WordCategoryClassificator();
        Map<String, ProjectContainer> projects = crawler.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        Map<String, WordCategoryContainer> markers = collector.getMarkers(projects);
        markers.values().stream().forEach(System.out::println);
    }
}
