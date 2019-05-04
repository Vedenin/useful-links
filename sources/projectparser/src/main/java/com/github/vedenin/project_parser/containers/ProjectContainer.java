package com.github.vedenin.project_parser.containers;

import lombok.Data;

/**
 * Container that storeresult information about github's project
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Data(staticConstructor = "create")
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
    public Boolean isExist = true;

    public boolean isEmpty() {
        return url.isEmpty();
    }
}
