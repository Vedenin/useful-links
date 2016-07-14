package com.github.vedenin.useful_links.crawlers.impl;

import com.github.vedenin.useful_links.common.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.GithubAndPageStatistics;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.github.vedenin.useful_links.common.utils.DownloadUtils.getInteger;
import static com.github.vedenin.useful_links.common.utils.DownloadUtils.getPage;
import static java.lang.Thread.sleep;

/**
 * Returns information about github's projects
 */
public class GithubAndPageStatisticsImpl implements GithubAndPageStatistics {
    /**
     * @inheritDoc
     */
    @Override
    public Map<String, ProjectContainer> getProjectWithGithubInfo(Map<String, ProjectContainer> map) {
        for(ProjectContainer p: map.values()) {
            String url = p.github != null && !p.github.isEmpty()? p.github: p.url;
            GithubInfoContainer info = getGithubInfo(url);
            p.newStars = info.stars;
            p.newForks = info.forks;
            p.newWatchs = info.watchs;
            p.pageText = info.text;
        }

        return map;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, GithubInfoContainer> getGithubInfoList(Set<String> urls) {
        Map<String, GithubInfoContainer> result = new LinkedHashMap<>(urls.size());
        for(String url: urls) {
            if(url.contains("github.com")) {
                try {
                    result.put(url, getGithubInfo(url));
                    sleep(100);
                } catch (Exception exp) {
                    System.out.println(exp.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    public GithubInfoContainer getGithubInfo(String url) {
        System.out.println("getGithubInfo " + url);
        GithubInfoContainer result = GithubInfoContainer.create();
        result.url = url;

        try {
            Document doc = getPage(url);
            result.text = doc.text();

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
