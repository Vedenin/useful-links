package com.github.vedenin.thirdpartylib.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Contract;
import com.github.vedenin.atom.annotations.Molecule;
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;


import static java.util.stream.Collectors.*;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@Atom(Document.class)
@Molecule({ElementAtom.class})
@Contract("Provide information about HTML pages")
public class DocumentAtom {
    private final Document document;

    @Contract("Should returns elements according this CSS Query")
    public List<ElementAtom> select(String cssQuery) {
        List<ElementAtom> result = new ArrayList<>();
        result.addAll(document.select(cssQuery).stream().map(ElementAtom::getAtom).collect(toList()));
        return result;
    }

    @Contract("Should returns text from html without any tags")
    public String getText() {
        return document.text();
    }


    // -------------- Just boilerplate code for Atom -----------------
    @BoilerPlate
    private DocumentAtom(Document document) {
        this.document = document;
    }

    @BoilerPlate
    static DocumentAtom getAtom(Document document) {
        return new DocumentAtom(document);
    }

}
