package com.github.vedenin.project_parser.containers;

import lombok.Data;

/**
 * Returns information about github's project info
 *
 * Created by Slava Vedenin on 5/11/2016.
 */
@Data(staticConstructor = "create")
public class GithubInfoContainer {
    private String url;
    private Integer stars;
    private Integer watchs;
    private Integer forks;
    private String text;
}
