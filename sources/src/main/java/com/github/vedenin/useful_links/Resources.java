package com.github.vedenin.useful_links;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Class returns data from resource files
 *
 * Created by vedenin on 04.06.16.
 */
public class Resources {

    public List<String> getNonProjectHeaders() {
        return getListFromConfig("/non_project_headers.config");
    }

    public List<String> getListFromConfig(String name) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(name);
            String str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return Arrays.asList(str.split("\n"));
        } catch (Exception e) {
            throw new ResourceException("Problem during open resource: " + name, e);
        }
    }
}
