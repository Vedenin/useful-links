package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.GithubStatistics;
import com.github.vedenin.useful_links.crawlers.JavaUsefulProjects;

import java.io.IOException;
import java.util.Map;

/**
 * Manager to downloading github project
 *
 * Created by vvedenin on 5/12/2016.
 */
public class MangerDownloading {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("1. Get JavaUsefulProjects");
        JavaUsefulProjects projectsDownloading = new JavaUsefulProjects();
        Map<String, ProjectContainer> project = projectsDownloading.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        System.out.println("2. Get Github statistics");
        GithubStatistics githubStatistics = new GithubStatistics();
        Map<String, GithubInfoContainer> statistics = githubStatistics.getGithubInfoList(project.keySet());
        statistics.values().stream().forEach(System.out::println);
    }
}