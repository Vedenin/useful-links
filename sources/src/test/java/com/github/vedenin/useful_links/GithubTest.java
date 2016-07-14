package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.common.Resources;
import com.github.vedenin.useful_links.common.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.DownloadProjects;
import com.github.vedenin.useful_links.crawlers.GithubLinkFinder;
import com.github.vedenin.useful_links.crawlers.GithubAndPageStatistics;
import com.github.vedenin.useful_links.crawlers.impl.DownloadProjectsImpl;
import com.github.vedenin.useful_links.crawlers.impl.GithubAndPageStatisticsImpl;
import com.github.vedenin.useful_links.crawlers.impl.GithubLinkFinderImpl;
import com.github.vedenin.useful_links.crawlers.old.JavaUsefulProjects;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

/**
 * Test github's
 *
 * Created by vvedenin on 7/12/2016.
 */
public class GithubTest {
    //@Test
    public void testGithubLinkFinder() {
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
        GithubLinkFinder githubLinkFinder = new GithubLinkFinderImpl();
        Map<String, ProjectContainer> projects = thisCls.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        Map<String, ProjectContainer> newProjects = githubLinkFinder.getGithubLinks(projects);
        newProjects.values().stream().forEach(System.out::println);
    }

    //@Test
    public void testGithubStatistics() {
        GithubAndPageStatisticsImpl thisCls = new GithubAndPageStatisticsImpl();
        GithubInfoContainer result = thisCls.getGithubInfo("https://github.com/Vedenin/useful-java-links");
        System.out.println(result);
    }

    @Test
    public void testGithubStaticticsNew() {
        Resources resources = new Resources();
        DownloadProjects downloadProjects = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        GithubAndPageStatistics githubStatistics = new GithubAndPageStatisticsImpl();
        URL url = this.getClass().getResource("/useful-java-links.html");
        Map<String, ProjectContainer> projects = downloadProjects.getProjects(url.toString());
        Map<String, ProjectContainer> result = githubStatistics.getProjectWithGithubInfo(projects);
        result.values().stream().forEach(System.out::println);
    }

}
