package com.github.vedenin.project_parser.crawlers.impl;

import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.htmlparser.DocumentAtom;
import com.github.vedenin.atoms.htmlparser.ElementAtom;
import com.github.vedenin.project_parser.crawlers.GithubLinkFinder;;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.vedenin.project_parser.Constants.GIT_HUB_URL;
import static com.github.vedenin.project_parser.Constants.GIT_HUB_URL_FULL;
import static com.github.vedenin.project_parser.downloader.utils.DownloadUtils.min;

/**
 * Find github link if project info without this link
 *
 * Created by Slava Vedenin on 5/12/2016.
 */
public class GithubLinkFinderImpl implements GithubLinkFinder {
    /**
     * @inheritDoc
     */
    @Override
    public String getGithubLink(DocumentAtom doc, String link) {
        if(!link.contains(GIT_HUB_URL)) {
            try {
                ListAtom<ElementAtom> elements = doc.select("a[href*=github.com]");
                String githubLink = getGithubLink(elements, link);
                if(githubLink != null) {
                    System.out.println("github's:" + link + " | " + githubLink);
                    return githubLink;
                } else {
                    return null;
                }
            } catch (Exception exp) {
                System.out.println(link + " : " + exp.getMessage());
            }
        }
        return null;
    }

    private static String getGithubLink(ListAtom<ElementAtom> elements, String site) {
        if(elements.isEmpty()) {
            return null;
        } else {
            Set<String> tokens = getToken(site);
            Set<String> set = new HashSet<>(elements.size());
            String candidate = null;
            for(ElementAtom element: elements) {
                String link = element.getAttr("href");
                String url = link.substring(link.indexOf(GIT_HUB_URL) + GIT_HUB_URL.length());
                int i1 = url.indexOf("/");
                if(i1 > -1) {
                    int i2 = min(url.indexOf("/", i1+1), url.indexOf("#", i1+1));
                    String key = i2 < 0? url: url.substring(0, i2);
                    set.add(key);
                    String text = element.getText().toLowerCase();
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
        String[] tokens = site.split(".");
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
