package com.github.vedenin.project_parser.classificators;

import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.collections.MultimapAtom;
import com.github.vedenin.project_parser.containers.CompareCategoryIndex;

import java.util.Map;
import java.util.TreeMap;

/**
 * Compare some set of categories according words in this category
 * <p>
 * Created by Slava Vedenin on 7/1/2016.
 */
public class WordsClassificator {

    /**
     * Compare two map with word and categories and returns all union count in category
     *
     * @param mapKnown   - map with word and categories
     * @param mapFinding - map with word and categories
     * @return category from first map and count common words from first and second map
     */
    public Map<String, Integer> getNumberOfOccurrences(MultimapAtom<String, String> mapKnown,
                                                       MultimapAtom<String, String> mapFinding) {
        Map<String, Integer> result = new TreeMap<>();
        for (String word : mapKnown.keySet()) {
            ListAtom<String> categoriesKnown = mapKnown.get(word);
            ListAtom<String> categoriesFinding = mapFinding.get(word);
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

    public Map<String, Integer> getCountWordByCategory(MultimapAtom<String, String> mapFinding) {
        Map<String, Integer> result = new TreeMap<>();
        for (String word : mapFinding.keySet()) {
            ListAtom<String> categoriesFinding = mapFinding.get(word);
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
    public Map<CompareCategoryIndex, Double> getCommonIndex(MultimapAtom<String, String> mapKnown,
                                                            MultimapAtom<String, String> mapFinding,
                                                            Map<String, Integer> cntKnownCategory,
                                                            Map<String, Integer> cntFindingCategory) {
        Map<CompareCategoryIndex, Double> result = new TreeMap<>();
        for (String word : mapFinding.keySet()) {
            ListAtom<String> knownCategory = mapKnown.get(word);
            ListAtom<String> findingCategory = mapFinding.get(word);
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
