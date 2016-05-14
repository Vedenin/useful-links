package com.github.vedenin.useful_links.classificator;

import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.JavaUsefulProjects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Get words from project about subjects
 *
 * Created by vedenin on 14.05.16.
 */
public class WordCollector {
    public static void main(String[] args) throws IOException {
        JavaUsefulProjects crawler = new JavaUsefulProjects();
        WordCollector collector = new WordCollector();
        Map<String, ProjectContainer> projects = crawler.getProjects("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
        Multimap<String, String> markers = collector.getMarkers(projects);

        projects.values().stream().forEach(System.out::println);
    }

    public Multimap<String, String> getMarkers(Map<String, ProjectContainer> projects) {
        Multimap<String, String> result = HashMultimap.create();
        for(ProjectContainer project: projects.values()){
            String text = project.description;
            String textWithoutNonChar = text.replaceAll("[^a-zA-Z0-9]"," ");
            String[] words = textWithoutNonChar.split(" ");
            String category = project.category;
            for(String word: words) {
                if(!word.isEmpty()) {
                    result.put(word, category);
                }
            }

        }
        return result;
    }
}
