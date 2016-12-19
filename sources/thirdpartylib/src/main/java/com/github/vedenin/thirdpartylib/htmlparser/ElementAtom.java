package com.github.vedenin.thirdpartylib.htmlparser;

import com.github.vedenin.thirdpartylib.annotations.Atom;
import com.github.vedenin.thirdpartylib.annotations.BoilerPlate;
import com.github.vedenin.thirdpartylib.annotations.Reference;
import org.jsoup.nodes.Element;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * Created by vvedenin on 12/16/2016.
 */
@Atom
@Reference({ElementAtom.class, TagAtom.class, DocumentAtom.class})
public class ElementAtom {
    private final Element element;

    public String getText() {
        return element.text();
    }

    public TagAtom getTag() {
        return TagAtom.getAtom(element.tag());
    }

    public String getAttr(String name) {
        return element.attr(name);
    }

    public String getOwnText() {
        return element.ownText();
    }

    public List<ElementAtom> getChild() {
        return element.children().stream().map(ElementAtom::getProxy).collect(toList());
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private ElementAtom(Element element) {
        this.element = element;
    }

    @BoilerPlate
    public static ElementAtom getProxy(Element element) {
        return new ElementAtom(element);
    }

}
