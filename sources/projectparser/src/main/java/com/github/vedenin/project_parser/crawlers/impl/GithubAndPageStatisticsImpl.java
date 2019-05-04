package com.github.vedenin.project_parser.crawlers.impl;

import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.htmlparser.DocumentAtom;
import com.github.vedenin.atoms.htmlparser.ElementAtom;
import com.github.vedenin.project_parser.containers.GithubInfoContainer;
import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.crawlers.GithubAndPageStatistics;
import com.github.vedenin.project_parser.crawlers.GithubLinkFinder;
import java.util.Map;

import static com.github.vedenin.project_parser.Constants.GIT_HUB_URL;
import static com.github.vedenin.project_parser.downloader.utils.DownloadUtils.getInteger;
import static com.github.vedenin.project_parser.downloader.utils.DownloadUtils.getPage;

/**
 * Returns information about github's projects
 */
public class GithubAndPageStatisticsImpl implements GithubAndPageStatistics {
    private final static GithubLinkFinder githubLinkFinder = new GithubLinkFinderImpl();

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, ProjectContainer> getProjectWithGithubInfo(Map<String, ProjectContainer> map) {
        for (ProjectContainer p : map.values()) {
            String url = p.getGithub() != null && !p.getGithub().isEmpty() ? p.getGithub() : p.getUrl();
            DocumentAtom doc = null;
            try {
                doc = getPage(url);
                p.setIsExist(true);
            } catch (Exception exp) {
                p.setIsExist(false);
            }
            if (p.getIsExist()) {
                if (p.getGithub() == null || p.getGithub().isEmpty()) {
                    if (p.getUrl().contains(GIT_HUB_URL)) {
                        p.setGithub(p.getUrl());
                    } else {
                        p.setGithub(githubLinkFinder.getGithubLink(doc, url));
                        if (p.getGithub() != null) {
                            try {
                                doc = getPage(p.getGithub());
                                url = p.getGithub();
                            } catch (Exception exp) {
                                System.out.println("Can't open github url's = " + p.getGithub());
                            }
                        }
                    }
                }
                GithubInfoContainer info = getGithubInfo(doc, url);
                p.setNewStars(info.getStars());
                p.setNewForks(info.getForks());
                p.setNewWatchs(info.getWatchs());
                p.setPageText(info.getUrl() == null ? "" : info.getText().replaceAll("\n", " ").replaceAll("\r", "").trim());
            }
        }
        return map;
    }


    /**
     * @inheritDoc
     */
    @Override
    public GithubInfoContainer getGithubInfo(DocumentAtom doc, String url) {
        GithubInfoContainer result = GithubInfoContainer.create();
        result.setText(doc.getText());
        try {

            ListAtom<ElementAtom> elements = doc.select("a[href*=/watchers]");
            result.setWatchs(getInteger(elements.get(0).getText()));

            elements = doc.select("a[href*=/stargazers]");
            result.setStars(getInteger(elements.get(0).getText()));

            elements = doc.select("a[href*=/network]");
            result.setForks(getInteger(elements.get(0).getText()));
        } catch (Exception exp) {
            System.out.println("Can't get github info from url " + url);
        }
        System.out.println(result);
        return result;
    }
}
