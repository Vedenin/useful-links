package com.github.vedenin.useful_links;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Class returns data from resource files
 *
 * Created by vedenin on 04.06.16.
 */
public class Resources {

    /**
     * Return all header that store non-project links (website and so on)
     * @return non-project header list
     */
    public List<String> getNonProjectHeaders() {
        return getListFromConfig("/non_project_headers.config");
    }

    public Map<String, String> getPopularLanguages() {
        return getMapFromConfig("/popular_languages.config");
    }
    /**
     * Return list from config file
     * @param name - name of config file
     * @return list from config file
     */
    public List<String> getListFromConfig(String name) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(name);
            String str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return Arrays.asList(str.split("\n"));
        } catch (Exception e) {
            throw new ResourceException("Problem during open resource: " + name, e);
        }
    }

    /**
     * Return map from config file (template: key1 := value1 \n key2 := value2)
     * @param name - name of config file
     * @return Map<key,value> from config file
     */
    public Map<String, String> getMapFromConfig(String name) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(name);
            String str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return Arrays.stream(str.split("\n")).collect(toMap((s) ->
                    s.split(" := ")[0], (s) ->  s.split(" := ")[1]
            ));
        } catch (Exception e) {
            throw new ResourceException("Problem during open resource: " + name, e);
        }
    }
}
