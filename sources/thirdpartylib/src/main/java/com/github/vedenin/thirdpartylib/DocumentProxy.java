package com.github.vedenin.thirdpartylib;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvedenin on 12/16/2016.
 */
public class DocumentProxy {
    private final Document document;

    public DocumentProxy(Document document) {
        this.document = document;
    }

    public static DocumentProxy getProxy(Document document) {
        return new DocumentProxy(document);
    }

    public List<ElementProxy> select(String cssQuery) {
        Elements elements = document.select(cssQuery);
        List<ElementProxy> result = new ArrayList<>(elements.size());
        for(Element element: elements) {
            result.add(ElementProxy.getProxy(element));
        }
        return result;
    }

    public String getText() {
        return document.text();
    }


}
