package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.containers.DownloadContext;
import com.github.vedenin.useful_links.containers.ProjectContainer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.vedenin.useful_links.Constants.GITHUB_STAR;
import static com.github.vedenin.useful_links.Constants.GIT_HUB_URL;
import static com.github.vedenin.useful_links.utils.DownloadUtils.*;

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
        Document doc = getPage(url);
        return parserSkippedSections(doc.select(README_TEG), DownloadContext.create());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, ProjectContainer> getProjects(String url) {
        Document doc = getPage(url);
        Elements title = doc.select(TITLE_TEG);
        String baseUrl = title.first().ownText().split(":")[0];
        Elements div = doc.select(README_TEG);
        return parserProjects(div, DownloadContext.create(baseUrl));
    }

    private List<String> parserSkippedSections(Elements elements, DownloadContext context) {
        List<String> result = new ArrayList<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                if(getSkipHeaderFlag(context, element.text(), getHeaderIndex(tag))) {
                    result.add(context.currentCategory);
                }
            }
            result.addAll(parserSkippedSections(element.children(), context));
        }
        return result;
    }

    private Map<String, ProjectContainer> parserProjects(Elements elements, DownloadContext context) {
        Map<String, ProjectContainer> result = new LinkedHashMap<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                context.skipHeaderFlag = getSkipHeaderFlag(context, element.text(), getHeaderIndex(tag));
            } else if(!context.skipHeaderFlag) {
                proceedBody(element, context, result);
            }
            result.putAll(parserProjects(element.children(), context));
        }
        return result;
    }

    private static void proceedBody(Element element, DownloadContext context,  Map<String, ProjectContainer> result) {
        Tag tag = element.tag();
        if(isEnum(tag)){
            context.isNewProject = true;
            context.description = getDescription(element);
        } else {
            if (isLink(tag)) {
                String link = element.attr(HREF_ATTR);
                if (isProjectLink(link, context.baseUrl)) {
                    if (isLicenseLink(link)) {
                        saveLicense(context.container, element, link);
                        System.out.println(context.baseUrl + " [license] " + link);
                    } else if (isSite(element, link)) {
                        saveSite(context.container, link);
                    } else if (isStackOverflow(link)) {
                        saveStackOverflow(context.container, element, link);
                    } else if (isUserGuide(element)) {
                        saveUserGuide(context.container, link);
                    } else {
                        context.container = getProjectContainer(context.currentCategory, context.description, element, link);
                        result.put(context.container.url, context.container);
                    }
                }
            }
        }
    }

    private static String getDescription(Element element) {
        return element.ownText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
    }

    private boolean getSkipHeaderFlag(DownloadContext context, String currentCategory, Integer headerIndex) {
        context.currentCategory = currentCategory;
        if(context.skipHeader == null) {
            if(isNonProjectHeader(context.currentCategory.toLowerCase(), nonProjectMainHeaders)) {
                context.skipHeader = headerIndex;
            }
        } else if(headerIndex < context.skipHeader) {
            context.skipHeader = null;
        }
        return isNonProjectHeader(context.currentCategory.toLowerCase(), nonProjectHeaders) || context.skipHeader != null;
    }

    private static void saveLicense(ProjectContainer container, Element element, String link) {
        if (container != null) {
            container.licenseUrl = link;
            container.license = element.text();
        }
    }

    private static void saveSite(ProjectContainer container, String link) {
        if (container != null) {
            container.site = link;
        }
    }

    private static void saveStackOverflow(ProjectContainer container, Element element, String link) {
        if (container != null) {
            try {
                container.stackOverflow = getInteger(element.text());
            } catch (Exception exp) {
                container.stackOverflow = null;
                System.out.println("saveStackOverflow " + link + ", " + element.text());
            }
            container.stackOverflowUrl = link;
        }
    }

    private static ProjectContainer getProjectContainer(String currentCategory, String text, Element element, String link) {
        ProjectContainer container;
        container = ProjectContainer.create();
        container.category = currentCategory;
        container.name = element.text();
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
