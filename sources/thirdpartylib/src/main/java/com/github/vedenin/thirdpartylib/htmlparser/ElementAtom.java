package com.github.vedenin.thirdpartylib.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import org.jsoup.nodes.Element;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@Atom(Element.class)
@Molecule({ElementAtom.class, TagAtom.class, DocumentAtom.class})
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
        return element.children().stream().map(ElementAtom::getAtom).collect(toList());
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private ElementAtom(Element element) {
        this.element = element;
    }

    @BoilerPlate
    static ElementAtom getAtom(Element element) {
        return new ElementAtom(element);
    }

}
