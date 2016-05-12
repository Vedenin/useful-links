package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.container.ProjectContainer;
import com.github.vedenin.useful_links.utils.HTTPSDownloadUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.vedenin.useful_links.utils.DownloadUtils.*;

/**
 * Download all projects from useful-java-links
 * <p>
 * Created by vedenin on 07.04.16.
 */
public class JavaUsefulProjectsDownloading {
    private static final String GITHUB_STAR = "github's star";

    public static void main(String[] s) throws IOException {
        JavaUsefulProjectsDownloading thisCls = new JavaUsefulProjectsDownloading();
        Map<String, ProjectContainer> projects = thisCls.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        projects.values().stream().forEach(System.out::println);
    }

    /**
     * Return list of java projects from:
     * - https://github.com/Vedenin/useful-java-links/blob/master/readme.md
     * - https://github.com/Vedenin/useful-java-links/blob/master/link-rus/readme.md
     *
     * @param url - url's from useful-java-links
     * @return java projects
     * @throws IOException
     */
    public Map<String, ProjectContainer> getProjects(String url) throws IOException {
        System.out.println("Start downloading");
        HTTPSDownloadUtils.initHTTPSDownload();
        Document doc = getPage(url);
        Elements div = doc.select("#readme");
        Map<String, ProjectContainer> result = parserProjects(div, "", null, "");
        System.out.println("End downloading");
        System.out.println();
        return result;
    }



    private static Map<String, ProjectContainer> parserProjects(Elements elements, String currentCategory, ProjectContainer container, String description) {
        Map<String, ProjectContainer> result = new HashMap<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                currentCategory = element.text();
                container = null;
                if("1. Communities".equals(currentCategory)) {
                    return result;
                }
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
                        result.put(container.url, container);
                    }
                }
            }
            result.putAll(parserProjects(element.children(), currentCategory, container, description));
        }
        return result;
    }

    private static void saveUserGuide(ProjectContainer container, String link) {
        container.userGuide = link;
    }

    private static String getDescription(Element element) {
        return element.ownText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
    }

    private static String getDescription(String text, String replacedText) {
        String result = text.replace(replacedText , "").
                replace(". and","").
                replaceAll("  ", " ").
                replace(" .",".").
                replace(" ,",",").
                replace(".,",".").
                replace(",.",".").
                replace("..",".").
                trim();

        return result.startsWith("-") ? result.substring(1).trim() : result;
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
        if(link.contains("github.com")) {
            container.github = link;
            container.url = link;
        } else {
            try {
                System.out.println("Find github's project:" + link);
                Document doc = getPage(link);
                Elements elements = doc.select("a[href*=github.com]");
                if(!elements.isEmpty()) {
                    Element element = elements.get(0);
                    String github = element.attr("href");
                    container.url = elements.size() == 1? github : link;
                    container.github = github;
                    container.site = link;
                } else {
                    container.url = link;
                }
            } catch (Exception exp) {
                System.out.println(link + " : " + exp.getMessage());
            }
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
            container.stackOverflow = getInteger(element.text());
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
                link.contains("eclipse.org/org/documents/") ||
                link.contains("gnu.org/copyleft/");
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


}
