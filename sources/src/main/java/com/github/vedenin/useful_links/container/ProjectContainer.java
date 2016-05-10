package com.github.vedenin.useful_links.container;

import com.github.vedenin.useful_links.annotation.PropertiesContainer;

/**
 * Container that store information about github's project
 *
 * Created by vvedenin on 5/10/2016.
 */
@PropertiesContainer // class without getter and setter (see Properties in C#)
public class ProjectContainer {
    private String category;
    private String name;
    private String url;
    private String description;
    private String star;
    private String stackOverflow;
    private String license;
    private String licenseUrl;
    private String site;

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
}
