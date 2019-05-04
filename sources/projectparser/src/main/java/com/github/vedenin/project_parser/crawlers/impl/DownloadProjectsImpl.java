package com.github.vedenin.project_parser.crawlers.impl;

import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.htmlparser.DocumentAtom;
import com.github.vedenin.atoms.htmlparser.ElementAtom;
import com.github.vedenin.atoms.htmlparser.TagAtom;
import com.github.vedenin.project_parser.containers.DownloadContext;
import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.crawlers.DownloadProjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.vedenin.core.downloader.utils.DownloadUtils.*;
import static com.github.vedenin.project_parser.Constants.GITHUB_STAR;
import static com.github.vedenin.project_parser.Constants.GIT_HUB_URL;

/**
 * @inheritDoc
 *
 * Created by vedenin on 04.06.16.
 */
public class DownloadProjectsImpl implements DownloadProjects {
    private static final String README_TEG = "#readme";
    private static final String TITLE_TEG = "title";
    private static final String HREF_ATTR = "href";

    private final List<String> nonProjectHeaders;
    private final List<String> nonProjectMainHeaders;

    public DownloadProjectsImpl(List<String> nonProjectHeaders, List<String> nonProjectMainHeaders) {
        this.nonProjectHeaders = getLowerCaseList(nonProjectHeaders);
        this.nonProjectMainHeaders = getLowerCaseList(nonProjectMainHeaders);
    }

    /**
     *  @inheritDoc
     */
    @Override
    public List<String> getSkippedSections(String url) {
        DocumentAtom doc = getPage(url);
        return parserSkippedSections(doc.select(README_TEG), DownloadContext.create());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, ProjectContainer> getProjects(String url) {
        DocumentAtom doc = getPage(url);
        ListAtom<ElementAtom> title = doc.select(TITLE_TEG);
        String baseUrl = title.get(0).getOwnText().split(":")[0];
        ListAtom<ElementAtom> div = doc.select(README_TEG);
        return parserProjects(div, DownloadContext.create(baseUrl));
    }

    private List<String> parserSkippedSections(ListAtom<ElementAtom> elements, DownloadContext context) {
        List<String> result = new ArrayList<>(elements.size());
        for (ElementAtom element : elements) {
            TagAtom tag = element.getTag();
            if (isHeader(tag)) {
                if(getSkipHeaderFlag(context, element.getText(), getHeaderIndex(tag))) {
                    result.add(context.getCurrentCategory());
                }
            }
            result.addAll(parserSkippedSections(element.getChild(), context));
        }
        return result;
    }

    private Map<String, ProjectContainer> parserProjects(ListAtom<ElementAtom> elements, DownloadContext context) {
        Map<String, ProjectContainer> result = new LinkedHashMap<>(elements.size());
        for (ElementAtom element : elements) {
            TagAtom tag = element.getTag();
            if (isHeader(tag)) {
                context.setSkipHeaderFlag(getSkipHeaderFlag(context, element.getText(), getHeaderIndex(tag)));
            } else if(!context.isSkipHeaderFlag()) {
                proceedBody(element, context, result);
            }
            result.putAll(parserProjects(element.getChild(), context));
        }
        return result;
    }

    private static void proceedBody(ElementAtom element, DownloadContext context,  Map<String, ProjectContainer> result) {
        TagAtom tag = element.getTag();
        if(isEnum(tag)){
            context.setSkipHeaderFlag(true);
            context.setDescription(getDescription(element));
        } else {
            if (isLink(tag)) {
                String link = element.getAttr(HREF_ATTR);
                if (isProjectLink(link, context.getBaseUrl())) {
                    if (isLicenseLink(link)) {
                        saveLicense(context.getContainer(), element, link);
                        System.out.println(context.getBaseUrl() + " [license] " + link);
                    } else if (isSite(element, link)) {
                        saveSite(context.getContainer(), link);
                    } else if (isStackOverflow(link)) {
                        saveStackOverflow(context.getContainer(), element, link);
                    } else if (isUserGuide(element)) {
                        saveUserGuide(context.getContainer(), link);
                    } else {
                        context.container = getProjectContainer(context.getCurrentCategory(), context.getDescription(), element, link);
                        result.put(context.getContainer().getUrl(), context.getContainer());
                    }
                }
            }
        }
    }

    private static String getDescription(ElementAtom element) {
        return element.getOwnText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
    }

    private boolean getSkipHeaderFlag(DownloadContext context, String currentCategory, Integer headerIndex) {
        context.setCurrentCategory(currentCategory);
        if(context.skipHeader == null) {
            if(isNonProjectHeader(context.getCurrentCategory().toLowerCase(), nonProjectMainHeaders)) {
                context.skipHeader = headerIndex;
            }
        } else if(headerIndex < context.skipHeader) {
            context.skipHeader = null;
        }
        return isNonProjectHeader(context.getCurrentCategory().toLowerCase(), nonProjectHeaders) || context.skipHeader != null;
    }

    private static void saveLicense(ProjectContainer container, ElementAtom element, String link) {
        if (container != null) {
            container.licenseUrl = link;
            container.license = element.getText();
        }
    }

    private static void saveSite(ProjectContainer container, String link) {
        if (container != null) {
            container.site = link;
        }
    }

    private static void saveStackOverflow(ProjectContainer container, ElementAtom element, String link) {
        if (container != null) {
            try {
                container.stackOverflow = getInteger(element.getText());
            } catch (Exception exp) {
                container.stackOverflow = null;
                System.out.println("saveStackOverflow " + link + ", " + element.getText());
            }
            container.stackOverflowUrl = link;
        }
    }

    private static ProjectContainer getProjectContainer(String currentCategory, String text, ElementAtom element, String link) {
        ProjectContainer container;
        container = ProjectContainer.create();
        container.category = currentCategory;
        container.name = element.getText();
        saveUrlAndGithub(link, container);
        saveStarAndText(container, text);
        return container;
    }

    private static void saveUrlAndGithub(String link, ProjectContainer container) {
        if(link.contains(GIT_HUB_URL)) {
            container.github = link;
            container.url = link;
        } else {
            container.url = link;
        }
    }

    private static void saveStarAndText(ProjectContainer container, String text) {
        int i1 = text.indexOf(GITHUB_STAR);
        if(i1 > -1) {
            int i2 = min(text.indexOf(".", i1), text.indexOf(",", i1));
            String starText = text.substring(i1, i2);
            container.star = getInteger(text.substring(i1 + GITHUB_STAR.length(), i2));
            container.description = getDescription(text, starText);
        } else {
            container.description = getDescription(text, "");
        }
        container.allText = container.description;
    }

    private static String getDescription(String text, String replacedText) {
        String result = text.replace(replacedText , "").
                replace(". and","").
                replaceAll("  ", " ").
                replace(" .",".").
                replace(" ,",",").
                replace(".,",".").
                replace(",.",".").
                replace("src/main",".").
                trim();
        return result.startsWith("-") ? result.substring(1).trim() : result;
    }

    private static void saveUserGuide(ProjectContainer container, String link) {
        container.userGuide = link;
    }
}
