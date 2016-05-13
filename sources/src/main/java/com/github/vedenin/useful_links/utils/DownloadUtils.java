package com.github.vedenin.useful_links.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Tag;

import java.io.IOException;

import static com.github.vedenin.useful_links.Constants.USER_AGENT;

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
    private static final Tag A = Tag.valueOf("a");


    public static boolean isHeader(Tag tag) {
        return H1.equals(tag) || H2.equals(tag) || H3.equals(tag) || H4.equals(tag) || H5.equals(tag) || H6.equals(tag);
    }

    public static boolean isEnum(Tag tag) {
        return LI.equals(tag);
    }

    public static boolean isLink(Tag tag) {
        return A.equals(tag);
    }

    private DownloadUtils() {}

    public static Document getPage(String url) throws IOException {
        HTTPSDownloadUtils.initHTTPSDownload();
        return Jsoup.connect(url).userAgent(USER_AGENT).timeout(30000).get();
    }

    public static Integer getInteger(String s) {
        return Integer.parseInt(s.replaceAll("[^\\d.]", ""));
    }

    public static int min(int i1, int i2) {
        return i1 < 0? i2: (i2< 0? i1 : Math.min(i1, i2));
    }
}
