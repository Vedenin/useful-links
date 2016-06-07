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

import static com.github.vedenin.useful_links.utils.DownloadUtils.getHeaderIndex;
import static com.github.vedenin.useful_links.utils.DownloadUtils.getPage;
import static com.github.vedenin.useful_links.utils.DownloadUtils.isHeader;
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
        Elements div = doc.select(README_TEG);
        return parserProjects(div, Context.create());
    }

    private List<String> parserSkippedSections(Elements elements, Context context) {
        List<String> result = new ArrayList<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                context.currentCategory = element.text();
                if(context.skipHeader == null) {
                    if(isNonProjectHeader(context.currentCategory.toLowerCase(), nonProjectMainHeaders)) {
                        context.skipHeader = getHeaderIndex(tag);
                    }
                } else if(getHeaderIndex(tag) < context.skipHeader) {
                    context.skipHeader = null;
                }
                if(isNonProjectHeader(context.currentCategory.toLowerCase(), nonProjectHeaders) || context.skipHeader != null) {
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
                context.currentCategory = element.text();
                context.container = null;
                if(isNonProjectHeader(context.currentCategory.toLowerCase(), nonProjectHeaders)) {
                    System.out.println(context.currentCategory);
                }
            }
            result.putAll(parserProjects(element.children(), context));
        }
        return result;
    }

    private static boolean isNonProjectHeader(String category, List<String> nonProjectHeaders) {
        return nonProjectHeaders.stream().anyMatch(category::contains);
    }

    @PropertiesContainer
    private static class Context {
        String currentCategory = "";
        ProjectContainer container = null;
        Integer skipHeader = null;
        String description = "";

        public static Context create() {
            return new Context();
        }

    }

    private List<String> getLowerCaseList(List<String> list) {
        return list.stream().peek(String::toLowerCase).peek(String::trim).collect(toList());
    }
}
