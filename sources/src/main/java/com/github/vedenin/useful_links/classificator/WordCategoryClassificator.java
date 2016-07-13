package com.github.vedenin.useful_links.classificator;

import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import com.github.vedenin.useful_links.common.containers.WordCategoryContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Get words from project about subjects
 *
 * Created by vedenin on 14.05.16.
 */
public class WordCategoryClassificator {
    public Map<String, WordCategoryContainer> getMarkers(Map<String, ProjectContainer> projects) {
        Map<String, Map<String, Integer>> tmpResult = getWordCategoryMap(projects);
        return getWordCategoryContainer(tmpResult);
    }

    // Returns Map<Word, Map<category, occurrences_of_this_word_to_category>
    private Map<String, Map<String, Integer>> getWordCategoryMap(Map<String, ProjectContainer> projects) {
        Map<String, Map<String, Integer>> tmpResult = new HashMap<>();
        for(ProjectContainer project: projects.values()){
            String text = project.allText;
            String textWithoutNonChar = text.replaceAll("[^a-zA-Z0-9]"," ");
            String[] words = textWithoutNonChar.split(" ");
            String category = project.category;
            for(String word: words) {
                if(!word.isEmpty()) {
                    Map<String, Integer> map = tmpResult.get(word);
                    if(map == null) {
                        map = new HashMap<>();
                        tmpResult.put(word, map);
                    }
                    Integer i = map.get(category);
                    map.put(category, i == null? 1: i+1);
                }
            }

        }
        return tmpResult;
    }

    // Returns Map<Word, Category_Info>
    private Map<String, WordCategoryContainer> getWordCategoryContainer(Map<String, Map<String, Integer>> tmpResult) {
        Map<String, WordCategoryContainer> result = new HashMap<>();
        for(Map.Entry<String, Map<String, Integer>> entry : tmpResult.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> value = entry.getValue();
            if(value.size() == 1) {
                String category = value.keySet().iterator().next();
                Integer occurrences = value.get(category);
                if(occurrences > 1) {
                    WordCategoryContainer container = WordCategoryContainer.create();
                    container.word = word;
                    container.category = category;
                    container.number = occurrences;
                    result.put(word, container);
                }
            }
        }
        return result;
    }
}
