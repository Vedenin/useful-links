package com.github.vedenin.thirdpartylib.htmlparser;

import com.github.vedenin.thirdpartylib.annotations.AtomUtils;
import com.github.vedenin.thirdpartylib.annotations.Reference;
import com.google.common.base.Charsets;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

/**
 * Created by vvedenin on 12/16/2016.
 */
@AtomUtils
@Reference({DocumentAtom.class})
public class HTMLParserAtom {
    private static String CHARSET_NAME = Charsets.UTF_8.name();

    public static DocumentAtom parseFile(File file) throws IOException {
        return DocumentAtom.getAtom(Jsoup.parse(file, CHARSET_NAME));
    }

    public static DocumentAtom parseUrl(String url, String userAgent, int timeout) throws IOException {
        return DocumentAtom.getAtom(Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get());
    }

}
