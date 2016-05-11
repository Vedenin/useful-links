package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.container.ProjectContainer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.github.vedenin.useful_links.utils.DownloadUtils.*;

/**
 * Download all projects from useful-java-links
 * <p>
 * Created by vedenin on 07.04.16.
 */
public class DownloadJavaUsefulProjects {
    private static final String GITHUB_STAR = "github's star";

    private static void testHtmlParser(String url) throws Exception {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).timeout(30000).get();
        Elements div = doc.select("#readme");
        List<ProjectContainer> list = parserProjects(div, "", null, "");
        list.stream().forEach(System.out::println);
    }

    private static List<ProjectContainer> parserProjects(Elements elements, String currentCategory, ProjectContainer container, String description) {
        List<ProjectContainer> result = new ArrayList<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                currentCategory = element.text();
                container = null;
            } else if (isEnum(tag)) {
                description = getDescription(element);
            } else if (isLink(tag)) {
                String link = element.attr("href");
                if (isProjectLink(element, link)) {
                    if (isLicenseLink(link)) {
                        saveLicense(container, element, link);
                    } else if (isSite(element, link)) {
                        saveSite(container, link);
                    } else if (isStackOverflow(link)) {
                        saveStackOverflow(container, element);
                    } else if(isUserGuide(element)) {
                        saveUserGuide(container, link);
                    } else {
                        container = getProjectContainer(currentCategory, description, element, link);
                        result.add(container);
                    }
                }
            }
            result.addAll(parserProjects(element.children(), currentCategory, container, description));
        }
        return result;
    }

    private static void saveUserGuide(ProjectContainer container, String link) {
        container.userGuide = link;
    }

    private static String getDescription(Element element) {
        String description;
        description = element.ownText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
        return description;
    }

    private static ProjectContainer getProjectContainer(String currentCategory, String text, Element element, String link) {
        ProjectContainer container;
        container = ProjectContainer.create();
        container.category = currentCategory;
        container.name = element.text();
        container.url = link;
        saveStarAndText(container, text);
        return container;
    }

    private static void saveStarAndText(ProjectContainer container, String text) {
        int i1 = text.indexOf(GITHUB_STAR);
        if(i1 > -1) {
            int i2 = min(text.indexOf(".", i1), text.indexOf(",", i1));
            String starText = text.substring(i1, i2);
            container.star = Integer.parseInt(text.substring(i1 + GITHUB_STAR.length(), i2).replaceAll("[^\\d.]", ""));
            container.description = text.replace(starText , "").replaceAll("  ", " ").replace(". ,",".").replace(", .",".").replace(".,",".").replace(",.",".").replace(". .",".");
        } else {
            container.description = text;
        }
    }

    private static void saveLicense(ProjectContainer container, Element element, String link) {
        if (container != null) {
            container.licenseUrl = link;
            container.license = element.text();
        }
    }

    private static int min(int i1, int i2) {
        return i1 < 0? i2: (i2< 0? i1 : Math.min(i1, i2));
    }

    private static void saveSite(ProjectContainer container, String link) {
        if (container != null) {
            container.site = link;
        }
    }

    private static void saveStackOverflow(ProjectContainer container, Element element) {
        if (container != null) {
            container.stackOverflow = Integer.parseInt(element.text().replaceAll("[^\\d.]", ""));
        }
    }

    private static boolean isSite(Element element, String link) {
        return link.equals(element.text().trim());
    }

    private static boolean isStackOverflow(String link) {
        return link.contains("stackoverflow.com");
    }

    private static boolean isLicenseLink(String link) {
        return link.contains("wikipedia.org") ||
                link.contains("/licenses/") ||
                link.contains("unlicense.org") ||
                link.contains("eclipse.org/org/documents/");
    }

    private static boolean isProjectLink(Element element, String link) {
        return !link.startsWith("#") &&
                !link.contains("/Vedenin/") &&
                !link.contains("useful-java-links") &&
                !link.contains("/akullpp/");
    }

    private static boolean isUserGuide(Element element) {
        return "User guide".equals(element.text());
    }

    public static void main(String[] s) throws Exception {
        testHtmlParser("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
    }

}
