package com.github.vedenin.useful_links;


import com.github.vedenin.useful_links.exceptions.ResourceException;
import org.apache.commons.io.IOUtils;

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

    /**
     * Return map all popular programming languages (key - github's key, name - languages name)
     * @return map<Github's_key, name_of_languages>
     */
    public Map<String, String> getPopularLanguages() {
        return getMapFromConfig("/popular_programming_languages.config");
    }

    /**
     * Return all header that store non-project links (website and so on) and other headers
     * @return non-project header list
     */
    public List<String> getNonProjectMainHeaders() {
        return getListFromConfig("/non_project_main_headers.config");
    }

    /**
     * Return map all programming languages from github (key - github's key, name - languages name)
     * @return map<Github's_key, name_of_languages>
     */
    public Map<String, String> getProgrammingLanguages() {
        return getMapFromConfig("/programming_languages.config");
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
            return Arrays.asList(str.replaceAll("\r","").split("\n"));
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
            return Arrays.stream(str.replaceAll("\r", "").split("\n")).map((s) ->
                    s.split(" := ")).collect(toMap((s) ->s[0], (s) ->  s[1]));
        } catch (Exception e) {
            throw new ResourceException("Problem during open resource: " + name, e);
        }
    }
}
