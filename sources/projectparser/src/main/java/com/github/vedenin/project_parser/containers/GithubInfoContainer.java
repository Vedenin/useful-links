package com.github.vedenin.project_parser.containers;

import com.github.vedenin.core.common.annotations.PropertiesContainer;
import lombok.Data;

/**
 * Returns information about github's project info
 *
 * Created by Slava Vedenin on 5/11/2016.
 */
@Data(staticConstructor = "create")
public class GithubInfoContainer {
    public String url;
    public Integer stars;
    public Integer watchs;
    public Integer forks;
    public String text;
}
