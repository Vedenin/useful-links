package com.github.vedenin.project_parser.crawlers.old;

import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.thirdpartylib.DocumentProxy;
import com.github.vedenin.thirdpartylib.ElementProxy;
import com.github.vedenin.thirdpartylib.TagProxy;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.vedenin.project_parser.Constants.GIT_HUB_URL;
import static com.github.vedenin.core.downloader.utils.DownloadUtils.*;

/**
 * Download all projects from useful-java-links
 * <p>
 * Created by vedenin on 07.04.16.
 */
public class JavaUsefulProjects {
    private static final String GITHUB_STAR = "github's star";

    public static void main(String[] s) throws IOException {
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
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
    public Map<String, ProjectContainer> getProjects(String url) {
        System.out.println("Start downloading");
        DocumentProxy doc = getPage(url);
        List<ElementProxy> div = doc.select("#readme");
        Map<String, ProjectContainer> result = parserProjects(div, "", null, "");
        System.out.println("End downloading");
        System.out.println();
        return result;
    }



    private static Map<String, ProjectContainer> parserProjects(List<ElementProxy> elements, String currentCategory, ProjectContainer container, String description) {
        Map<String, ProjectContainer> result = new LinkedHashMap<>(elements.size());
        for (ElementProxy element : elements) {
            TagProxy tag = element.getTag();
            if (isHeader(tag)) {
                currentCategory = element.getText();
                container = null;
                if("1. Communities".equals(currentCategory)) {
                    return result;
                }
            } else if (isEnum(tag)) {
                description = getDescription(element);
            } else if (isLink(tag)) {
                String link = element.getAttr("href");
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
            result.putAll(parserProjects(element.getChild(), currentCategory, container, description));
        }
        return result;
    }

    private static void saveUserGuide(ProjectContainer container, String link) {
        container.userGuide = link;
    }

    private static String getDescription(ElementProxy element) {
        return element.getOwnText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
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

    private static ProjectContainer getProjectContainer(String currentCategory, String text, ElementProxy element, String link) {
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

    private static void saveLicense(ProjectContainer container, ElementProxy element, String link) {
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

    private static void saveStackOverflow(ProjectContainer container, ElementProxy element) {
        if (container != null) {
            container.stackOverflow = getInteger(element.getText());
        }
    }

    private static boolean isSite(ElementProxy element, String link) {
        return link.equals(element.getText().trim());
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

    private static boolean isProjectLink(ElementProxy element, String link) {
        return !link.startsWith("#") &&
                !link.contains("/Vedenin/") &&
                !link.contains("useful-java-links") &&
                !link.contains("/akullpp/");
    }

    private static boolean isUserGuide(ElementProxy element) {
        return "User guide".equals(element.getText());
    }


}
