package com.github.vedenin.useful_links.project_parser.containers;

import com.github.vedenin.useful_links.core.common.annotations.PropertiesContainer;

/**
 * Context to DownloadProjectsImpl class
 *
 * Created by vedenin on 12.06.16.
 */
@PropertiesContainer
public class DownloadContext {
    public String currentCategory = "";
    public ProjectContainer container = null;
    public Integer skipHeader = null;
    public boolean skipHeaderFlag = false;
    public boolean isNewProject = false;
    public String description = "";
    public String baseUrl = null;

    public static DownloadContext create() {
        return new DownloadContext();
    }

    public static DownloadContext create(String baseUrl) {
        DownloadContext context = new DownloadContext();
        context.baseUrl = baseUrl;
        return context;
    }
}
