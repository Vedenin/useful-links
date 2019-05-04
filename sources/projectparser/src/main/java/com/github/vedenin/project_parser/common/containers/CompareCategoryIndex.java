package com.github.vedenin.project_parser.common.containers;

import lombok.Data;

/**
 * Container for comparisons categories
 *
 * Created by Slava Vedenin on 7/1/2016.
 */
@Data(staticConstructor = "create")
public class CompareCategoryIndex implements Comparable {
    private String knownCategory; // Category that mapping we already known
    private String findingCategory; // Category that mapping we need to find

    public static CompareCategoryIndex create(String knownCategory, String findingCategory) {
        CompareCategoryIndex result = new CompareCategoryIndex();
        result.findingCategory = findingCategory;
        result.knownCategory = knownCategory;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof CompareCategoryIndex) {
            int r = this.findingCategory.compareTo(((CompareCategoryIndex) o).findingCategory);
            if(r == 0) {
                return this.knownCategory.compareTo(((CompareCategoryIndex) o).knownCategory);
            } else {
                return r;
            }
        }
        return 0;
    }
}
