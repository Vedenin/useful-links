package com.github.vedenin.project_parser;

import com.github.vedenin.core.resources.Resources;
import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.crawlers.DownloadProjects;
import com.github.vedenin.project_parser.crawlers.impl.DownloadProjectsImpl;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test DownloadProjects Crawlers
 *
 * Created by vedenin on 04.06.16.
 */
public class DownloadProjectsTests {
    @Test
    public void getSkippedSectionsTest() {
        Resources resources = new Resources();
        DownloadProjects thisCls = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        List<String> awesomeLists = resources.getListFromConfig("/awesome-lists/lists.txt");
        System.out.println("useful-java-links");
        List<String> skippedSections = getSkippedSections(thisCls, "/useful-java-links.html");
        skippedSections.stream().forEach(System.out::println);
        for(String awesomeList: awesomeLists) {
            System.out.println(awesomeList +"\n");
            List<String> result = getSkippedSections(thisCls, "/awesome-lists/" + awesomeList.trim());
            skippedSections.addAll(result);
            result.stream().forEach(System.out::println);
        }
        assertEquals(195, skippedSections.size());
    }

    private List<String> getSkippedSections(DownloadProjects thisCls, String fileName) {
        URL url = this.getClass().getResource(fileName);
        return thisCls.getSkippedSections(url.toString());
    }

    @Test
    public void getProjectsTest() {
        Resources resources = new Resources();
        DownloadProjects thisCls = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        List<String> awesomeLists = resources.getListFromConfig("/awesome-lists/lists.txt");
        System.out.println("useful-java-links");
        Map<String, ProjectContainer> projects = getProjects(thisCls, "/useful-java-links.html");
        projects.values().stream().forEach(System.out::println);
        for(String awesomeList: awesomeLists) {
            System.out.println(awesomeList +"\n");
            Map<String, ProjectContainer> result = getProjects(thisCls, "/awesome-lists/" + awesomeList.trim());
            projects.putAll(result);
            result.values().stream().forEach(System.out::println);
        }
        assertEquals(7300, projects.size());
    }

    private Map<String, ProjectContainer> getProjects(DownloadProjects thisCls, String fileName) {
        URL url = this.getClass().getResource(fileName);
        return thisCls.getProjects(url.toString());
    }

}
