package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.AwesomeProjectsNew;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by vedenin on 04.06.16.
 */
public class AwesomeProjectsNewTests {
    @Test
    public void testDownload() throws IOException {
        AwesomeProjectsNew thisCls = new AwesomeProjectsNew();

        Resources resources = new Resources();
        List<String> nonProjectHeaders = resources.getNonProjectHeaders();
        List<String> awesomeLists = resources.getListFromConfig("/awesome-lists/lists.txt");
        workingProjects(thisCls, nonProjectHeaders, "/useful-java-links.html");
        for(String awesomeList: awesomeLists) {
            workingProjects(thisCls, nonProjectHeaders, "/awesome-lists/" + awesomeList);
        }
    }

    private void workingProjects(AwesomeProjectsNew thisCls, List<String> nonProjectHeaders, String fileName) throws IOException {
        URL url = this.getClass().getResource(fileName);
        System.out.println();
        System.out.println(fileName + " : ");
        Map<String, ProjectContainer> projects = thisCls.getProjects(url.toString(), nonProjectHeaders);
        projects.values().stream().forEach(System.out::println);
    }
}
