package com.github.vedenin.project_parser;

import com.github.vedenin.project_parser.classificators.WordCategoryClassificator;
import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.containers.WordCategoryContainer;
import com.github.vedenin.project_parser.crawlers.old.JavaUsefulProjects;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Slava Vedenin on 7/12/2016.
 */
public class WordCategoryClassificatorTest {
    @Test
    @Ignore
    public void testWordCategoryClassificator() {
        JavaUsefulProjects crawler = new JavaUsefulProjects();
        WordCategoryClassificator collector = new WordCategoryClassificator();
        Map<String, ProjectContainer> projects = crawler.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        Map<String, WordCategoryContainer> markers = collector.getMarkers(projects);
        markers.values().stream().forEach(System.out::println);
    }
}
