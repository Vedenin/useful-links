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

    public DownloadProjectsImpl(List<String> nonProjectHeaders) {
        this.nonProjectHeaders = nonProjectHeaders.stream().peek(String::toLowerCase).peek(String::trim).collect(toList());
    }

    /**
     *  @inheritDoc
     */
    @Override
    public List<String> getSkippedSections(String url) {
        Document doc = getPage(url);
        return parserSkippedSections(doc.select(README_TEG), Context.create(nonProjectHeaders));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, ProjectContainer> getProjects(String url) {
        Document doc = getPage(url);
        Elements div = doc.select(README_TEG);
        return parserProjects(div, Context.create(nonProjectHeaders));
    }

    private static List<String> parserSkippedSections(Elements elements, Context context) {
        List<String> result = new ArrayList<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                context.currentCategory = element.text();
                if(isNonProjectHeader(context.currentCategory.toLowerCase(), context.nonProjectHeaders)) {
                    result.add(context.currentCategory);
                }
            }
            result.addAll(parserSkippedSections(element.children(), context));
        }
        return result;
    }

    private static Map<String, ProjectContainer> parserProjects(Elements elements, Context context) {
        Map<String, ProjectContainer> result = new LinkedHashMap<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                context.currentCategory = element.text();
                context.container = null;
                if(isNonProjectHeader(context.currentCategory.toLowerCase(), context.nonProjectHeaders)) {
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
        String description = "";
        List<String> nonProjectHeaders;

        public static Context create() {
            return new Context();
        }

        public static Context create(List<String> nonProjectHeaders) {
            Context context = new Context();
            context.nonProjectHeaders = nonProjectHeaders;
            return  context;
        }

    }
}
