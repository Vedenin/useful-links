package com.github.vedenin.project_parser.crawlers;

import com.github.vedenin.atoms.htmlparser.DocumentAtom;
import com.github.vedenin.project_parser.containers.GithubInfoContainer;
import com.github.vedenin.project_parser.containers.ProjectContainer;

import java.util.Map;

/**
 * Returns information about github's projects
 *
 * Created by vedenin on 14.07.16.
 */
public interface GithubAndPageStatistics {
    /**
     * Returns map with filled github's info (if find)
     * @param map - map url + ProjectContainer
     * @return map url + ProjectContainer with github info
     */
    Map<String, ProjectContainer> getProjectWithGithubInfo(Map<String, ProjectContainer> map);
    /**
     * Returns information about Github'project (stars, forks and so on)
     * @param url Github'project url
     * @return github's info
     */
    GithubInfoContainer getGithubInfo(DocumentAtom doc, String url);
}
