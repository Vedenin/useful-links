package com.github.vedenin.useful_links.common.utils;

import com.github.vedenin.useful_links.crawlers.downloader.Downloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by vvedenin on 5/10/2016.
 */
public final class DownloadUtils {
    private static final Tag H1 = Tag.valueOf("h1");
    private static final Tag H2 = Tag.valueOf("h2");
    private static final Tag H3 = Tag.valueOf("h3");
    private static final Tag H4 = Tag.valueOf("h4");
    private static final Tag H5 = Tag.valueOf("h5");
    private static final Tag H6 = Tag.valueOf("h6");
    private static final Tag LI = Tag.valueOf("li");
    private static final Map<Tag, Integer> headersIndexMap = new HashMap<>(6);
    private static final Tag A = Tag.valueOf("a");

    static {
        headersIndexMap.put(H1, 1);
        headersIndexMap.put(H2, 2);
        headersIndexMap.put(H3, 3);
        headersIndexMap.put(H4, 4);
        headersIndexMap.put(H5, 5);
        headersIndexMap.put(H6, 6);
    }

    public static boolean isHeader(Tag tag) {
        return H1.equals(tag) || H2.equals(tag) || H3.equals(tag) || H4.equals(tag) || H5.equals(tag) || H6.equals(tag);
    }

    public static Integer getHeaderIndex(Tag tag) {
        return headersIndexMap.get(tag);
    }

    public static boolean isEnum(Tag tag) {
        return LI.equals(tag);
    }

    public static boolean isLink(Tag tag) {
        return A.equals(tag);
    }

    private DownloadUtils() {
    }

    public static Document getPage(String url) {
        return Downloader.instance().getPage(url);
    }

    public static Integer getInteger(String s) {
            return Integer.parseInt(s.replaceAll("[^\\d.]", ""));
    }

    public static int min(int i1, int i2) {
        return i1 < 0 ? i2 : (i2 < 0 ? i1 : Math.min(i1, i2));
    }


    public static List<String> getLowerCaseList(List<String> list) {
        return list.stream().peek(String::toLowerCase).peek(String::trim).collect(toList());
    }

    public static boolean isLicenseLink(String link) {
        String lowerCase = link.toLowerCase();
        return  lowerCase.contains("wikipedia.org") ||
                lowerCase.contains("wikibooks.org") ||
                lowerCase.contains("licens") ||
                lowerCase.contains("licenc") ||
                lowerCase.contains("wtfpl.net") ||
                lowerCase.contains("unlicense.org") ||
                lowerCase.contains("eclipse.org/org/documents/") ||
                lowerCase.contains("gnu.org/copyleft/");
    }

    public static boolean isProjectLink(String link, String baseUrl) {
        String lowerCase = link.toLowerCase();
        return !lowerCase.startsWith("#") &&
                !lowerCase.contains(baseUrl.toLowerCase()) &&
                !lowerCase.contains("awesome");
    }

    public static boolean isNonProjectHeader(String category, List<String> nonProjectHeaders) {
        return nonProjectHeaders.stream().anyMatch(category::contains);
    }

    public static boolean isSite(Element element, String link) {
        return link.equals(element.text().trim());
    }

    public static boolean isStackOverflow(String link) {
        return link.contains("stackoverflow.com");
    }

    public static boolean isUserGuide(Element element) {
        return "User guide".equals(element.text());
    }


}
