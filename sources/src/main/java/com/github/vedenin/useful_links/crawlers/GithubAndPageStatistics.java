package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.common.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;

import java.util.Map;
import java.util.Set;

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
     * Returns information about Github'projects (stars, forks and so on)
     *
     * @param urls Github'projects urls
     * @return map <url + github's infon>
     */
    Map<String, GithubInfoContainer> getGithubInfoList(Set<String> urls);

    /**
     * Returns information about Github'project (stars, forks and so on)
     * @param url Github'project url
     * @return github's info
     */
    GithubInfoContainer getGithubInfo(String url);
}
