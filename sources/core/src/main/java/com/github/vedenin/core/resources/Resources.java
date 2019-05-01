package com.github.vedenin.core.resources;


import com.github.vedenin.atoms.io.FileAndIOUtilsAtom;
import com.github.vedenin.atoms.io.FileAtom;
import com.github.vedenin.core.common.exceptions.ResourceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;


/**
 * Class returns data from resource files
 * <p>
 * Created by vedenin on 04.06.16.
 */
public class Resources {

    public Set<String> getTwoLangBooks(String dirPath) {
        return getFiles(dirPath).stream()
                .map((s) -> s.replaceAll("_eng.txt", ""))
                .map((s) -> s.replaceAll("_rus.txt", ""))
                .collect(Collectors.toSet());
    }

    /**
     * Return all header that storeresult non-project links (website and so on)
     *
     * @return non-project header list
     */
    public List<String> getNonProjectHeaders() {
        return getListFromConfig("/non_project_headers.config");
    }

    /**
     * Return map all popular programming languages (key - github's key, name - languages name)
     *
     * @return map<Github ' s_key, name_of_languages>
     */
    public Map<String, String> getPopularLanguages() {
        return getMapFromConfig("/popular_programming_languages.config");
    }

    /**
     * Return all header that storeresult non-project links (website and so on) and other headers
     *
     * @return non-project header list
     */
    public List<String> getNonProjectMainHeaders() {
        return getListFromConfig("/non_project_main_headers.config");
    }

    /**
     * Return map all programming languages from github (key - github's key, name - languages name)
     *
     * @return map<Github ' s_key, name_of_languages>
     */
    public Map<String, String> getProgrammingLanguages() {
        return getMapFromConfig("/programming_languages.config");
    }

    /**
     * Returns list from config file
     *
     * @param name - name of config file
     * @return list from config file
     */
    public List<String> getListFromConfig(String name) {
        return Arrays.asList(getResourceAsString(name).replaceAll("\r", "").split("\n")).stream().
                map(String::trim).collect(Collectors.toList());
    }

    /**
     * Returns list from file (every row is separate item in list)
     *
     * @param name -file name
     * @return list from file
     */
    public List<String> getListFromFile(String name) {
        return FileAndIOUtilsAtom.readLines(FileAtom.create(name)).getOriginal();
    }

    /**
     * Return map from config file (template: key1 := value1 \n key2 := value2)
     *
     * @param name - name of config file
     * @return Map<key, value> from config file
     */
    public Map<String, String> getMapFromConfig(String name) {
        return Arrays.stream(getResourceAsString(name).replaceAll("\r", "").split("\n")).map((s) ->
                s.split(" := ")).collect(toMap((s) -> s[0], (s) -> s[1]));
    }

    /**
     * Returns String from resource file
     *
     * @param name - path and name of resource file
     * @return String from this resource file
     */
    public String getResourceAsString(String name) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(name);
            return FileAndIOUtilsAtom.readIOtoString(inputStream);
        } catch (Exception e) {
            throw new ResourceException("Problem during open resource: " + name, e);
        }
    }

    /**
     * Returns list of files path from all resources of this dir
     *
     * @param dirPath - dir path
     * @return list of files path
     */
    public List<String> getFiles(String dirPath) {
        FileAtom dir = getDir(dirPath);
        return FileAndIOUtilsAtom.listFiles(dir).stream().map(FileAtom::getPath).collect(Collectors.toList());
    }

    /**
     * Returns list of sorted files from all resources of this dir
     *
     * @param dirPath - dir path
     * @return list of files path
     */
    public TreeSet<String> getSortedFiles(String dirPath) {
        return new TreeSet<>(getFiles(dirPath));
    }

    /**
     * Returns file from recourse's dir path
     *
     * @param dirPath - dir path
     * @return dir file
     */
    public FileAtom getDir(String dirPath) {
        URL url = this.getClass().getResource(dirPath);
        return FileAtom.create(url.getPath());
    }

    /**
     * Save text as resource in target directory
     *
     * @param dirPath  - path for dir
     * @param fileName - file name
     * @param text     - text that need to save
     */
    public void saveAsResource(String dirPath, String fileName, String text) {
        Resources resources = new Resources();
        File dir = resources.getDir(dirPath).getOriginal();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileAtom file = FileAtom.create(dir.getPath() + "/" + fileName);

        if (file.createNewFile()) {
            FileAndIOUtilsAtom.writeStringToFile(file, text);
        }
    }

    /**
     * Save text as resource in target directory
     *
     * @param dirPath  - path for dir
     * @param fileName - file name
     * @param lines    - List of lines that need to save
     */
    public void saveAsResource(String dirPath, String fileName, List<String> lines) {
        saveAsResource(dirPath, fileName, lines.stream().collect(Collectors.joining("\n\r")));
    }

    /**
     * Returns String from file
     *
     * @param name - path and name of file
     * @return String from this file
     */
    public String getFileAsString(String name) {
        try {
            return FileAndIOUtilsAtom.readFileToString(FileAtom.create(name));
        } catch (Exception e) {
            throw new ResourceException("Problem during open file: " + name, e);
        }
    }

    public String getNameFromResource(String name, String dir) {
        return name.replace(dir, "").replace("\\", "").replace("/", "").split("_")[0];
    }

    public String getNameFromResource(String name) {
        String tmp = name.replace("\\", "/");
        return tmp.substring(tmp.lastIndexOf("/") + 1);
    }

    public String getResourcePath(String name) {
        try {
            URL url = this.getClass().getResource(name);
            return url.getPath();
        } catch (Exception e) {
            throw new ResourceException("Problem during open resource: " + name, e);
        }
    }
}
