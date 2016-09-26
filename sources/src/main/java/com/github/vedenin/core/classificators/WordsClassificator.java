package com.github.vedenin.core.classificators;

import com.github.vedenin.core.common.containers.CompareCategoryIndex;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Compare some set of categories according words in this category
 * <p>
 * Created by vvedenin on 7/1/2016.
 */
public class WordsClassificator {

    /**
     * Compare two map with word and categories and returns all union count in category
     *
     * @param mapKnown   - map with word and categories
     * @param mapFinding - map with word and categories
     * @return category from first map and count common words from first and second map
     */
    public Map<String, Integer> getNumberOfOccurrences(Multimap<String, String> mapKnown, Multimap<String, String> mapFinding) {
        Map<String, Integer> result = new TreeMap<>();
        for (String word : mapKnown.keySet()) {
            Collection<String> categoriesKnown = mapKnown.get(word);
            Collection<String> categoriesFinding = mapFinding.get(word);
            for (String categoryFinding : categoriesFinding) {
                for (String categoryKnown : categoriesKnown) {
                    result.put(categoryKnown, result.containsKey(categoryKnown) ? result.get(categoryKnown) + 1 : 1);
                }
            }
        }
        return result;
    }

    /**
     * Returns all words in every category
     *
     * @param mapFinding - map with word and categories
     * @return map with category and count of all words
     */

    public Map<String, Integer> getCountWordByCategory(Multimap<String, String> mapFinding) {
        Map<String, Integer> result = new TreeMap<>();
        for (String word : mapFinding.keySet()) {
            Collection<String> categoriesFinding = mapFinding.get(word);
            for (String categoryFinding : categoriesFinding) {
                result.put(categoryFinding, result.containsKey(categoryFinding) ? result.get(categoryFinding) + 1 : 1);
            }
        }
        return result;
    }

    /**
     * Returns common index for two category
     *
     * @param mapKnown
     * @param mapFinding
     * @param cntKnownCategory
     * @param cntFindingCategory
     * @return
     */
    public Map<CompareCategoryIndex, Double> getCommonIndex(Multimap<String, String> mapKnown, Multimap<String, String> mapFinding,
                                                            Map<String, Integer> cntKnownCategory, Map<String, Integer> cntFindingCategory) {
        Map<CompareCategoryIndex, Double> result = new TreeMap<>();
        for (String word : mapFinding.keySet()) {
            Collection<String> knownCategory = mapKnown.get(word);
            Collection<String> findingCategory = mapFinding.get(word);
            for (String author : knownCategory) {
                for (String anonymous : findingCategory) {
                    CompareCategoryIndex key = CompareCategoryIndex.create(author, anonymous);
                    Double index = 1000000.0 / (double) (cntFindingCategory.get(anonymous) * cntKnownCategory.get(author));
                    if (!result.containsKey(key)) {
                        result.put(key, index);
                    } else {
                        result.put(key, result.get(key) + index);
                    }
                }
            }
        }
        return result;
    }
}
