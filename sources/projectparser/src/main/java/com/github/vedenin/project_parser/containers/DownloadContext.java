package com.github.vedenin.project_parser.containers;

import lombok.Data;

/**
 * Context to DownloadProjectsImpl class
 *
 * Created by vedenin on 12.06.16.
 */
@Data(staticConstructor = "create")
public class DownloadContext {
    public String currentCategory = "";
    public ProjectContainer container = null;
    public Integer skipHeader = null;
    public boolean skipHeaderFlag = false;
    public boolean isNewProject = false;
    private String description = "";
    private String baseUrl = null;

    public static DownloadContext create(String baseUrl) {
        DownloadContext context = new DownloadContext();
        context.setBaseUrl(baseUrl);
        return context;
    }
}
