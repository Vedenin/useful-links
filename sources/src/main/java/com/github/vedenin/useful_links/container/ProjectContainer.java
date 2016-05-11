package com.github.vedenin.useful_links.container;

import com.github.vedenin.useful_links.annotation.PropertiesContainer;

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

    @Override
    public String toString() {
        return "{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                (url != null?", url='" + url + '\'' : "") +
                (star != null?", star='" + star + '\'' : "") +
                (stackOverflow != null?", stackOverflow='" + stackOverflow + '\'' : "") +
                (license != null?", license='" + license + '\'' : "") +
                (site != null?", site='" + site + '\'' : "") +
                (licenseUrl != null?", licenseUrl='" + licenseUrl + '\'' : "") +
                (userGuide != null?", userGuide='" + userGuide + '\'' : "") +
                (description != null?", description='" + description + '\'' : "") +
                '}';
    }

    public static ProjectContainer create() {
        return new ProjectContainer();
    }
}
