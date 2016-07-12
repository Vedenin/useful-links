package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.GithubStatistics;
import com.github.vedenin.useful_links.crawlers.old.JavaUsefulProjects;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static com.github.vedenin.useful_links.crawlers.GithubLinkFinder.getGithubLinks;

/**
 * Test github's
 *
 * Created by vvedenin on 7/12/2016.
 */
public class GithubTest {
    @Test
    public void testGithubLinkFinder() {
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
        Map<String, ProjectContainer> projects = thisCls.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        Map<String, ProjectContainer> newProjects = getGithubLinks(projects);
        newProjects.values().stream().forEach(System.out::println);
    }

    @Test
    public void testGithubStatistics() {
        GithubStatistics thisCls = new GithubStatistics();
        GithubInfoContainer result = thisCls.getGithubInfo("https://github.com/Vedenin/useful-java-links");
        System.out.println(result);
    }

}
