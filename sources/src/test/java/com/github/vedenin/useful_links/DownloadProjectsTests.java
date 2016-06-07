package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.crawlers.DownloadProjects;
import com.github.vedenin.useful_links.crawlers.DownloadProjectsImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test DownloadProjects Crawlers
 *
 * Created by vedenin on 04.06.16.
 */
public class DownloadProjectsTests {
    @Test
    public void getSkippedSectionsTest() throws IOException {
        Resources resources = new Resources();
        DownloadProjects thisCls = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        List<String> awesomeLists = resources.getListFromConfig("/awesome-lists/lists.txt");
        System.out.println("useful-java-links");
        List<String> skippedSections = workingProjects(thisCls, "/useful-java-links.html");
        skippedSections.stream().forEach(System.out::println);
        for(String awesomeList: awesomeLists) {
            System.out.println(awesomeList +"\n");
            List<String> result = workingProjects(thisCls, "/awesome-lists/" + awesomeList.trim());
            skippedSections.addAll(result);
            result.stream().forEach(System.out::println);
        }
        assertEquals(195, skippedSections.size());
    }

    private List<String> workingProjects(DownloadProjects thisCls, String fileName) throws IOException {
        URL url = this.getClass().getResource(fileName);
        return thisCls.getSkippedSections(url.toString());
    }
}
