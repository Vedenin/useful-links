package com.github.vedenin.project_parser.crawlers;

import com.github.vedenin.atoms.htmlparser.DocumentAtom;

/**
 * Find github link if project info without this link
 * <p>
 * Created by Slava Vedenin on 7/13/2016.
 */
public interface GithubLinkFinder {
    /**
     * Returns github info to this url and document
     * @param doc - html document
     * @param link - url
     * @return github link's or null
     */
    String getGithubLink(DocumentAtom doc, String link);


}
