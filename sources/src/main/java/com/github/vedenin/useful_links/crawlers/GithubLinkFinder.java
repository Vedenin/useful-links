package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.common.containers.ProjectContainer;

import java.util.Map;

/**
 * Find github link if project info without this link
 *
 * Created by vvedenin on 7/13/2016.
 */
public interface GithubLinkFinder {
    Map<String, ProjectContainer> getGithubLinks(Map<String, ProjectContainer> projectContainerMap);
    void saveGithubLink(String link, ProjectContainer container);

}
