package com.github.vedenin.useful_links.utils;

import org.jsoup.parser.Tag;

/**
 * Created by vvedenin on 5/10/2016.
 */
final class DownloadUtils {
    static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    static final Tag H1 = Tag.valueOf("h1");
    static final Tag H2 = Tag.valueOf("h2");
    static final Tag H3 = Tag.valueOf("h3");
    static final Tag H4 = Tag.valueOf("h4");
    static final Tag H5 = Tag.valueOf("h5");
    static final Tag H6 = Tag.valueOf("h6");

    static boolean isHeader(Tag tag) {
        return H1.equals(tag) || H2.equals(tag) || H3.equals(tag) || H4.equals(tag) || H5.equals(tag) || H6.equals(tag);
    }

    private DownloadUtils() {}
}
