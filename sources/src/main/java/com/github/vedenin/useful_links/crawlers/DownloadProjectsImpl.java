package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.annotations.PropertiesContainer;
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
import static java.util.stream.Collectors.toList;

/**
 * @inheritDoc
 *
 * Created by vedenin on 04.06.16.
 */
public class DownloadProjectsImpl implements DownloadProjects {
    private static final String README_TEG = "#readme";
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
        return parserSkippedSections(doc.select(README_TEG), Context.create());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, ProjectContainer> getProjects(String url) {
        Document doc = getPage(url);
        Elements title = doc.select("title");
        String baseUrl = title.first().ownText().split(":")[0];
        Elements div = doc.select(README_TEG);
        return parserProjects(div, Context.create(baseUrl));
    }

    private List<String> parserSkippedSections(Elements elements, Context context) {
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

    private Map<String, ProjectContainer> parserProjects(Elements elements, Context context) {
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

    private static void proceedBody(Element element, Context context,  Map<String, ProjectContainer> result) {
        Tag tag = element.tag();
        if(isEnum(tag)){
            context.isNewProject = true;
            context.description = getDescription(element);
        } else {
            if (isLink(tag)) {
                String link = element.attr("href");
                if (isProjectLink(link, context.baseUrl)) {
                    System.out.println(context.baseUrl + " " + link);
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

    private static boolean isProjectLink(String link, String baseUrl) {
        String linkToLowerCase = link.toLowerCase();
        return !link.startsWith("#") &&
                !linkToLowerCase.contains(baseUrl.toLowerCase()) &&
                !linkToLowerCase.contains("awesome");
    }

    private static String getDescription(Element element) {
        return element.ownText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
    }

    private boolean getSkipHeaderFlag(Context context, String currentCategory, Integer headerIndex) {
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

    private static boolean isNonProjectHeader(String category, List<String> nonProjectHeaders) {
        return nonProjectHeaders.stream().anyMatch(category::contains);
    }

    @PropertiesContainer
    private static class Context {
        String currentCategory = "";
        ProjectContainer container = null;
        Integer skipHeader = null;
        boolean skipHeaderFlag = false;
        boolean isNewProject = false;
        String description = "";
        String baseUrl = null;

        public static Context create() {
            return new Context();
        }

        public static Context create(String baseUrl) {
            Context context = new Context();
            context.baseUrl = baseUrl;
            return context;
        }
    }

    private List<String> getLowerCaseList(List<String> list) {
        return list.stream().peek(String::toLowerCase).peek(String::trim).collect(toList());
    }
}
