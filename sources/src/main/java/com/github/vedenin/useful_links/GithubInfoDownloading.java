package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.container.GithubInfo;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.vedenin.useful_links.utils.DownloadUtils.getInteger;
import static com.github.vedenin.useful_links.utils.DownloadUtils.getPage;
import static java.lang.Thread.sleep;

/**
 * Returns information about github's projects
 */
public class GithubInfoDownloading {

    public static void main(String[] args) throws IOException {
        GithubInfoDownloading thisCls = new GithubInfoDownloading();
        GithubInfo result = thisCls.getGithubInfo("https://github.com/Vedenin/useful-java-links");
        System.out.println(result);
    }

    public Map<String, GithubInfo> getGithubInfoList(Set<String> urls) throws IOException, InterruptedException {
        Map<String, GithubInfo> result = new HashMap<>(urls.size());
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

    public GithubInfo getGithubInfo(String url) throws IOException {
        System.out.println("getGithubInfo " + url);
        GithubInfo result = GithubInfo.create();
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
