package com.github.vedenin.core.downloader.utils;

import com.github.vedenin.thirdpartylib.DocumentProxy;
import com.github.vedenin.thirdpartylib.ElementProxy;
import com.github.vedenin.core.downloader.Downloader;
import com.github.vedenin.thirdpartylib.TagProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by vvedenin on 5/10/2016.
 */
public final class DownloadUtils {
    private static final TagProxy H1 = TagProxy.valueOf("h1");
    private static final TagProxy H2 = TagProxy.valueOf("h2");
    private static final TagProxy H3 = TagProxy.valueOf("h3");
    private static final TagProxy H4 = TagProxy.valueOf("h4");
    private static final TagProxy H5 = TagProxy.valueOf("h5");
    private static final TagProxy H6 = TagProxy.valueOf("h6");
    private static final TagProxy LI = TagProxy.valueOf("li");
    private static final Map<TagProxy, Integer> headersIndexMap = new HashMap<>(6);
    private static final TagProxy A = TagProxy.valueOf("a");

    static {
        headersIndexMap.put(H1, 1);
        headersIndexMap.put(H2, 2);
        headersIndexMap.put(H3, 3);
        headersIndexMap.put(H4, 4);
        headersIndexMap.put(H5, 5);
        headersIndexMap.put(H6, 6);
    }

    public static boolean isHeader(TagProxy TagProxy) {
        return H1.equals(TagProxy) || H2.equals(TagProxy) || H3.equals(TagProxy) || H4.equals(TagProxy) || H5.equals(TagProxy) || H6.equals(TagProxy);
    }

    public static Integer getHeaderIndex(TagProxy TagProxy) {
        return headersIndexMap.get(TagProxy);
    }

    public static boolean isEnum(TagProxy TagProxy) {
        return LI.equals(TagProxy);
    }

    public static boolean isLink(TagProxy TagProxy) {
        return A.equals(TagProxy);
    }

    private DownloadUtils() {
    }

    public static DocumentProxy getPage(String url) {
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

    public static boolean isSite(ElementProxy element, String link) {
        return link.equals(element.getText().trim());
    }

    public static boolean isStackOverflow(String link) {
        return link.contains("stackoverflow.com");
    }

    public static boolean isUserGuide(ElementProxy element) {
        return "User guide".equals(element.getText());
    }


}
