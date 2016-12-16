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

    public static DocumentProxy parseFile(File file) throws IOException {
        return DocumentProxy.getProxy(Jsoup.parse(file, CHARSET_NAME));
    }

    public static DocumentProxy parseUrl(String url, String userAgent, int timeout) throws IOException {
        return DocumentProxy.getProxy(Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get());
    }

}
