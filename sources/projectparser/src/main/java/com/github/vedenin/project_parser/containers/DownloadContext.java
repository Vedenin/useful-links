package com.github.vedenin.project_parser.containers;

import lombok.Data;

/**
 * Context to DownloadProjectsImpl class
 *
 * Created by vedenin on 12.06.16.
 */
@Data(staticConstructor = "create")
public class DownloadContext {
    private String currentCategory = "";
    private ProjectContainer container = null;
    private Integer skipHeader = null;
    private boolean skipHeaderFlag = false;
    private boolean isNewProject = false;
    private String description = "";
    private String baseUrl = null;

    public static DownloadContext create(String baseUrl) {
        DownloadContext context = new DownloadContext();
        context.setBaseUrl(baseUrl);
        return context;
    }
}
