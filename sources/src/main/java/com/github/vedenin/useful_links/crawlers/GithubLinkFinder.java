package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.common.containers.ProjectContainer;

import java.util.Map;

/**
 * Find github link if project info without this link
 *
 * Created by vvedenin on 7/13/2016.
 */
public interface GithubLinkFinder {
    /**
     * Returns ProjectContainer with filled github links (if it's find)
     *
     * @param projectContainerMap - Map url + ProjectContainer
     * @return map url+ ProjectContainer with filled github links
     */
    Map<String, ProjectContainer> getGithubLinks(Map<String, ProjectContainer> projectContainerMap);

    /**
     * Save github info to Project container
     * @param link - url
     * @param container - Project container
     */
    void saveGithubLink(String link, ProjectContainer container);

}
