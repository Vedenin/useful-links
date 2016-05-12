package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.containers.ProjectContainer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

import static com.github.vedenin.useful_links.Constants.GIT_HUB_URL;
import static com.github.vedenin.useful_links.Constants.GIT_HUB_URL_FULL;
import static com.github.vedenin.useful_links.utils.DownloadUtils.getPage;
import static com.github.vedenin.useful_links.utils.DownloadUtils.*;

/**
 * Created by vvedenin on 5/12/2016.
 */
public class GithubLinkFinder {
    public static void main(String[] args) {

    }

    private static void saveUrlAndGithub(String link, ProjectContainer container) {
        if(link.contains(GIT_HUB_URL)) {
            container.github = link;
            container.url = link;
        } else {
            try {
                Document doc = getPage(link);
                Elements elements = doc.select("a[href*=github.com]");
                container.url = link;
                String githubLink = getGithubLink(elements);
                if(githubLink != null) {
                    container.url = githubLink;
                    container.github = githubLink;
                    container.site = link;
                    System.out.println("github's:" + link + " | " + githubLink);
                } else {
                    container.url = link;
                }
            } catch (Exception exp) {
                System.out.println(link + " : " + exp.getMessage());
            }
        }
    }

    private static String getGithubLink(Elements elements) {
        if(elements.isEmpty()) {
            return null;
        } else {
            Set<String> set = new HashSet<>(elements.size());
            String candidate = null;
            for(Element element: elements) {
                String link = element.attr("href");
                String url = link.substring(link.indexOf(GIT_HUB_URL) + GIT_HUB_URL.length());
                int i1 = url.indexOf("/");
                if(i1 > -1) {
                    int i2 = min(url.indexOf("/", i1+1), url.indexOf("#", i1+1));
                    String key = i2 < 0? url: url.substring(0, i2);
                    set.add(GIT_HUB_URL_FULL + key);
                    String text = element.text().toLowerCase();
                    if(text.contains("fork") || text.contains("source code")) {
                        candidate = GIT_HUB_URL_FULL + key;
                    }
                }
            }
            if(set.isEmpty()) {
                return null;
            } else {
                if(set.size() == 1) {
                    return set.iterator().next();
                } else if(candidate != null){
                    return candidate;
                } else {
                    return null;
                }
            }
        }

    }
}