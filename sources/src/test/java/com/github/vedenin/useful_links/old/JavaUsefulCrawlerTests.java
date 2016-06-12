package com.github.vedenin.useful_links.old;

import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.old.JavaUsefulProjects;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests for testing JavaUsefulProjects Crawler
 *
 * Created by vedenin on 03.06.16.
 */
public class JavaUsefulCrawlerTests {
    @Test
    public void test() throws IOException {
        URL url = this.getClass().getResource("/useful-java-links.html");
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
        Map<String, ProjectContainer> projects = thisCls.getProjects(url.toString());
        assertEquals(732, projects.size());
        projects.values().stream().forEach(System.out::println);
    }
}
