package com.github.vedenin.useful_links.project_parser.crawlers.impl;

import com.github.vedenin.useful_links.project_parser.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.project_parser.containers.ProjectContainer;
import com.github.vedenin.useful_links.project_parser.crawlers.GithubAndPageStatistics;
import com.github.vedenin.useful_links.project_parser.crawlers.GithubLinkFinder;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

import static com.github.vedenin.useful_links.project_parser.Constants.GIT_HUB_URL;
import static com.github.vedenin.useful_links.core.downloader.utils.DownloadUtils.getInteger;
import static com.github.vedenin.useful_links.core.downloader.utils.DownloadUtils.getPage;

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
            String url = p.github != null && !p.github.isEmpty() ? p.github : p.url;
            Document doc = null;
            try {
                doc = getPage(url);
                p.isExist = true;
            } catch (Exception exp) {
                p.isExist = false;
            }
            if (p.isExist) {
                if (p.github == null || p.github.isEmpty()) {
                    if (p.url.contains(GIT_HUB_URL)) {
                        p.github = p.url;
                    } else {
                        p.github = githubLinkFinder.getGithubLink(doc, url);
                        if (p.github != null) {
                            try {
                                doc = getPage(p.github);
                                url = p.github;
                            } catch (Exception exp) {
                                System.out.println("Can't open github url's = " + p.github);
                            }
                        }
                    }
                }
                GithubInfoContainer info = getGithubInfo(doc, url);
                p.newStars = info.stars;
                p.newForks = info.forks;
                p.newWatchs = info.watchs;
                p.pageText = info.text == null ? "" : info.text.replaceAll("\n", " ").replaceAll("\r", "").trim();
            }
        }
        return map;
    }


    /**
     * @inheritDoc
     */
    @Override
    public GithubInfoContainer getGithubInfo(Document doc, String url) {
        GithubInfoContainer result = GithubInfoContainer.create();
        result.text = doc.text();
        try {

            Elements elements = doc.select("a[href*=/watchers]");
            result.watchs = getInteger(elements.get(0).text());

            elements = doc.select("a[href*=/stargazers]");
            result.stars = getInteger(elements.get(0).text());

            elements = doc.select("a[href*=/network]");
            result.forks = getInteger(elements.get(0).text());
        } catch (Exception exp) {
            System.out.println("Can't get github info from url " + url);
        }
        System.out.println(result);
        return result;
    }
}
