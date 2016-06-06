package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.crawlers.DownloadProjects;
import com.github.vedenin.useful_links.crawlers.DownloadProjectsImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by vedenin on 04.06.16.
 */
public class DownloadProjectsTests {
    @Test
    public void testDownload() throws IOException {
        Resources resources = new Resources();
        DownloadProjects thisCls = new DownloadProjectsImpl(resources.getNonProjectHeaders());

        List<String> awesomeLists = resources.getListFromConfig("/awesome-lists/lists.txt");
        workingProjects(thisCls, "/useful-java-links.html");
        for(String awesomeList: awesomeLists) {
            workingProjects(thisCls, "/awesome-lists/" + awesomeList.trim());
        }
    }

    private void workingProjects(DownloadProjects thisCls, String fileName) throws IOException {
        URL url = this.getClass().getResource(fileName);
        System.out.println();
        System.out.println(fileName + " : ");
        List<String> skippedSections = thisCls.getSkippedSections(url.toString());
        skippedSections.stream().forEach(System.out::println);
    }
}
