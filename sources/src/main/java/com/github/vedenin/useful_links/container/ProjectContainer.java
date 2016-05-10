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
    public String star;
    public String stackOverflow;
    public String license;
    public String licenseUrl;
    public String site;

    @Override
    public String toString() {
        return "{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", star='" + star + '\'' +
                ", stackOverflow='" + stackOverflow + '\'' +
                ", license='" + license + '\'' +
                ", licenseUrl='" + licenseUrl + '\'' +
                ", site='" + site + '\'' +
                '}';
    }

    public static ProjectContainer create() {
        return new ProjectContainer();
    }
}
