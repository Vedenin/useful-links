package com.github.vedenin.thirdpartylib;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by vvedenin on 12/16/2016.
 */
public class ElementProxy {
    private final Element element;

    public ElementProxy(Element element) {
        this.element = element;
    }

    public static ElementProxy getProxy(Element element) {
        return new ElementProxy(element);
    }

    public String getText() {
        return element.text();
    }

    public Tag getTag() {
        return element.tag();
    }

    public String getAttr(String name) {
        return element.attr(name);
    }

    public String getOwnText() {
        return element.ownText();
    }

    public List<ElementProxy> getChild() {
        return element.children().stream().map(ElementProxy::getProxy).collect(toList());
    }

}
