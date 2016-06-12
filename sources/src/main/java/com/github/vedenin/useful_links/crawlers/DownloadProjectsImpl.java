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
                    } else {
                        System.out.println(context.baseUrl + " " + link);
                    }
                    /*if (isLicenseLink(link)) {
                        saveLicense(container, element, link);
                    } else if (isSite(element, link)) {
                        saveSite(container, link);
                    } else if (isStackOverflow(link)) {
                        saveStackOverflow(container, element);
                    } else if (isUserGuide(element)) {
                        saveUserGuide(container, link);
                    } else {
                        container = getProjectContainer(currentCategory, description, element, link);
                        result.put(container.url, container);
                    }*/
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
}
