package com.github.vedenin.project_parser.crawlers;

import com.github.vedenin.project_parser.containers.ProjectContainer;

import java.util.List;
import java.util.Map;

/**
 * Returns Programming Projects from awesome lists or useful links
 *
 * Created by vvedenin on 6/6/2016.
 */
public interface DownloadProjects {
    /**
     * Returns sections that has non-projects links (like websites or articles)
     *
     * @return list of sections that has non-projects links (like websites or articles)
     */
    List<String> getSkippedSections(String url);

    /**
     * Return list of java projects from awesome lists or useful links:
     *
     * @param url - url's from useful-java-links
     * @return java projects
     */
    Map<String, ProjectContainer> getProjects(String url);
}
