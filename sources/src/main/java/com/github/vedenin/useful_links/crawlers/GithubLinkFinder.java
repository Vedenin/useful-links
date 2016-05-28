package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.sun.deploy.util.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.github.vedenin.useful_links.Constants.GIT_HUB_URL;
import static com.github.vedenin.useful_links.Constants.GIT_HUB_URL_FULL;
import static com.github.vedenin.useful_links.utils.DownloadUtils.getPage;
import static com.github.vedenin.useful_links.utils.DownloadUtils.min;

/**
 * Find github link if project info without this link
 *
 * Created by vvedenin on 5/12/2016.
 */
public class GithubLinkFinder {
    public static void main(String[] args) throws IOException {
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
        Map<String, ProjectContainer> projects = thisCls.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        Map<String, ProjectContainer> newProjects = getGithubLinks(projects);
        newProjects.values().stream().forEach(System.out::println);
    }

    public static Map<String, ProjectContainer> getGithubLinks(Map<String, ProjectContainer> projectContainerMap) {
        projectContainerMap.forEach(GithubLinkFinder::saveGithubLink);
        return projectContainerMap;
    }

    public static void saveGithubLink(String link, ProjectContainer container) {
        if(!link.contains(GIT_HUB_URL)) {
            try {
                Document doc = getPage(link);
                Elements elements = doc.select("a[href*=github.com]");
                container.url = link;
                String githubLink = getGithubLink(elements, link);
                if(githubLink != null) {
                    container.url = githubLink;
                    container.github = githubLink;
                    container.site = link;
                    System.out.println("github's:" + link + " | " + githubLink);
                }
            } catch (Exception exp) {
                System.out.println(link + " : " + exp.getMessage());
            }
        }
    }


    private static String getGithubLink(Elements elements, String site) {
        if(elements.isEmpty()) {
            return null;
        } else {
            Set<String> tokens = getToken(site);
            Set<String> set = new HashSet<>(elements.size());
            String candidate = null;
            for(Element element: elements) {
                String link = element.attr("href");
                String url = link.substring(link.indexOf(GIT_HUB_URL) + GIT_HUB_URL.length());
                int i1 = url.indexOf("/");
                if(i1 > -1) {
                    int i2 = min(url.indexOf("/", i1+1), url.indexOf("#", i1+1));
                    String key = i2 < 0? url: url.substring(0, i2);
                    set.add(key);
                    String text = element.text().toLowerCase();
                    if(text.contains("fork") || text.contains("source code") || allMatch(key, tokens)) {
                        if(candidate != null && !key.equals(candidate)) {
                            candidate = candidate.length() < key.length()? candidate: key;
                        } else {
                            candidate = key;
                        }
                    }
                }
            }
            if(set.isEmpty()) {
                return null;
            } else {
                if(set.size() == 1) {
                    String key = set.iterator().next();
                    if(anyMatch(key, tokens)) {
                        return getGithubUrl(key);
                    } else {
                        String siteLowCase = replaceString(site, "-", "_");
                        String[] keys = replaceString(key, "-", "_").split("/");
                        for(String k: keys) {
                            if(siteLowCase.contains(k)) {
                                return getGithubUrl(key);
                            }
                        }
                        return null;
                    }
                } else if(candidate != null){
                    return getGithubUrl(candidate);
                } else {
                    return null;
                }
            }
        }
    }

    private static Set<String> getToken(String url) {
        String urlWithoutPerfix = replaceString(url, "http://", "https://");
        String site = getSubString(urlWithoutPerfix, "/", "#");
        String[] tokens = StringUtils.splitString(site, ".");
        LinkedHashSet<String> set = new LinkedHashSet<>(tokens.length-1);
        int i = 1;
        for(String token: tokens) {
            if(!"www".equals(token) && i < tokens.length) {
                set.add(token);
            }
            i++;
        }
        return set;
    }

    private static String getSubString(String original, String substr1, String substr2) {
        int i1 = min(original.indexOf(substr1), original.indexOf(substr2));
        return i1<0 ? original : original.substring(0,i1);
    }

    private static String replaceString(String url, String r1, String r2) {
        return url.toLowerCase().replace(r1,"").replace(r2,"");
    }

    private static String getGithubUrl(String key) {
        return GIT_HUB_URL_FULL + key;
    }

    private static boolean anyMatch(String string, Set<String> set) {
        return set.stream().anyMatch(string.toLowerCase()::contains);
    }

    private static boolean allMatch(String string, Set<String> set) {
        return set.stream().allMatch(string.toLowerCase()::contains);
    }

}
