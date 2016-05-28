package com.github.vedenin.useful_links.containers;

import com.github.vedenin.useful_links.annotations.PropertiesContainer;

/**
 * Container that store information about github's project
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
    public String license;
    public String licenseUrl;
    public String site;
    public String userGuide;
    public Integer newStar;
    public Integer newStackOverflow;
    public String github;
    public String allText;

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
                toString(newStar, "newStar") +
                toString(newStackOverflow, "newStackOverflow") +
                toString(github, "github") +
                toString(allText, "allText") +
                '}';
    }

    private static String toString(Object value, String name) {
        return value != null?", " + name + "='" + value + '\'' : "";
    }

    public static ProjectContainer create() {
        return new ProjectContainer();
    }
}
