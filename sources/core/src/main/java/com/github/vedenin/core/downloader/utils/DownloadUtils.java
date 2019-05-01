package com.github.vedenin.core.downloader.utils;

import com.github.vedenin.atoms.htmlparser.DocumentAtom;
import com.github.vedenin.atoms.htmlparser.ElementAtom;
import com.github.vedenin.atoms.htmlparser.TagAtom;
import com.github.vedenin.core.downloader.Downloader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by Slava Vedenin on 5/10/2016.
 */
public final class DownloadUtils {
    private static final TagAtom H1 = TagAtom.valueOf("h1");
    private static final TagAtom H2 = TagAtom.valueOf("h2");
    private static final TagAtom H3 = TagAtom.valueOf("h3");
    private static final TagAtom H4 = TagAtom.valueOf("h4");
    private static final TagAtom H5 = TagAtom.valueOf("h5");
    private static final TagAtom H6 = TagAtom.valueOf("h6");
    private static final TagAtom LI = TagAtom.valueOf("li");
    private static final Map<TagAtom, Integer> headersIndexMap = new HashMap<>(6);
    private static final TagAtom A = TagAtom.valueOf("a");

    static {
        headersIndexMap.put(H1, 1);
        headersIndexMap.put(H2, 2);
        headersIndexMap.put(H3, 3);
        headersIndexMap.put(H4, 4);
        headersIndexMap.put(H5, 5);
        headersIndexMap.put(H6, 6);
    }

    public static boolean isHeader(TagAtom TagAtom) {
        return H1.equals(TagAtom) || H2.equals(TagAtom) || H3.equals(TagAtom) || H4.equals(TagAtom) || H5.equals(TagAtom) || H6.equals(TagAtom);
    }

    public static Integer getHeaderIndex(TagAtom TagAtom) {
        return headersIndexMap.get(TagAtom);
    }

    public static boolean isEnum(TagAtom TagAtom) {
        return LI.equals(TagAtom);
    }

    public static boolean isLink(TagAtom TagAtom) {
        return A.equals(TagAtom);
    }

    private DownloadUtils() {
    }

    public static DocumentAtom getPage(String url) {
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

    public static boolean isSite(ElementAtom element, String link) {
        return link.equals(element.getText().trim());
    }

    public static boolean isStackOverflow(String link) {
        return link.contains("stackoverflow.com");
    }

    public static boolean isUserGuide(ElementAtom element) {
        return "User guide".equals(element.getText());
    }


}
