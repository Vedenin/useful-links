package com.github.vedenin.project_parser.containers;

import lombok.Data;

/**
 * Container that storeresult information about github's project
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Data(staticConstructor = "create")
public class ProjectContainer {
    private String category;
    private String name;
    private String url;
    private String description;
    private Integer star;
    private Integer stackOverflow;
    private String stackOverflowUrl;
    private String license;
    private String licenseUrl;
    private String site;
    private String userGuide;
    private Integer newStars;
    private Integer newWatchs;
    private Integer newForks;
    private String pageText;
    private Integer newStackOverflow;
    private String github;
    private String allText;
    private Boolean isExist = true;
}
