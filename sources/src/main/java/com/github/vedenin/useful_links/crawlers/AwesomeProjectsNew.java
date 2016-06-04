package com.github.vedenin.useful_links.crawlers;

import com.github.vedenin.useful_links.annotations.PropertiesContainer;
import com.github.vedenin.useful_links.containers.ProjectContainer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.vedenin.useful_links.utils.DownloadUtils.getPage;
import static com.github.vedenin.useful_links.utils.DownloadUtils.isHeader;

/**
 * Created by vedenin on 04.06.16.
 */
public class AwesomeProjectsNew {
    private static final String GITHUB_STAR = "github's star";
    /**
     * Return list of java projects from:
     * - https://github.com/Vedenin/useful-java-links/blob/master/readme.md
     * - https://github.com/Vedenin/useful-java-links/blob/master/link-rus/readme.md
     *
     * @param url - url's from useful-java-links
     * @return java projects
     * @throws IOException
     */
    public Map<String, ProjectContainer> getProjects(String url, List<String> nonProjectHeaders) throws IOException {
        Document doc = getPage(url);
        Elements div = doc.select("#readme");
        return parserProjects(div, Context.create(nonProjectHeaders));
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
