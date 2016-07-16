package com.github.vedenin.useful_links.common.containers;

import com.github.vedenin.useful_links.common.annotations.PropertiesContainer;

/**
 * Container that storeresult information about github's project
 *
 * Created by vvedenin on 5/10/2016.
 */
@PropertiesContainer // class without getter and setter (see Properties in C#)
public class ProjectContainer {
    public String category;
    public String name;
    public String url;
    public String description;
    public Integer star;
    public Integer stackOverflow;
    public String stackOverflowUrl;
    public String license;
    public String licenseUrl;
    public String site;
    public String userGuide;
    public Integer newStars;
    public Integer newWatchs;
    public Integer newForks;
    public String pageText;
    public Integer newStackOverflow;
    public String github;
    public String allText;
    public boolean isExist = true;

    @Override
    public String toString() {
        return "{" +
                "category = '" + category + "'" +
                toString(name, "name") +
                toString(url, "url") +
                toString(star, "star") +
                toString(stackOverflow, "stackOverflow") +
                toString(license, "license") +
                toString(site, "site") +
                toString(licenseUrl, "licenseUrl") +
                toString(userGuide, "userGuide") +
                toString(description, "description") +
                toString(newStars, "newStars") +
                toString(newStackOverflow, "newStackOverflow") +
                toString(github, "github") +
                toString(allText, "allText") +
                toString(stackOverflowUrl, "stackOverflowUrl") +
                '}';
    }

    public boolean isEmpty() {
        return url.isEmpty();
    }

    private static String toString(Object value, String name) {
        return value != null?", " + name + "='" + value + '\'' : "";
    }

    public static ProjectContainer create() {
        return new ProjectContainer();
    }
}
