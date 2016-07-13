package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.common.containers.GithubInfoContainer;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.github.vedenin.useful_links.common.utils.DownloadUtils.getInteger;
import static com.github.vedenin.useful_links.common.utils.DownloadUtils.getPage;
import static java.lang.Thread.sleep;

/**
 * Returns information about github's projects
 */
public class GithubStatistics {

    public Map<String, GithubInfoContainer> getGithubInfoList(Set<String> urls) throws IOException, InterruptedException {
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

    public GithubInfoContainer getGithubInfo(String url) {
        System.out.println("getGithubInfo " + url);
        GithubInfoContainer result = GithubInfoContainer.create();
        result.url = url;

        Document doc = getPage(url);

        Elements elements = doc.select("a[href*=/watchers]");
        result.watchs = getInteger(elements.get(0).text());

        elements = doc.select("a[href*=/stargazers]");
        result.stars = getInteger(elements.get(0).text());

        elements = doc.select("a[href*=/network]");
        result.forks = getInteger(elements.get(0).text());
        System.out.println(result);
        return result;
    }
}
