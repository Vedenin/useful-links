package com.github.vedenin.thirdpartylib;

import com.google.common.base.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * Created by vvedenin on 12/16/2016.
 */
public class HTMLParserProxy {
    private static String CHARSET_NAME = Charsets.UTF_8.name();

    public static Document parseFile(File file) throws IOException {
        return Jsoup.parse(file, CHARSET_NAME);
    }

    public static Document parseUrl(String url, String userAgent, int timeout) throws IOException {
        return Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get();
    }
}
