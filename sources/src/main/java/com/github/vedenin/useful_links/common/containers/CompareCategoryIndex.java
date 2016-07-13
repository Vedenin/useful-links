package com.github.vedenin.useful_links.common.containers;


import com.github.vedenin.useful_links.common.annotations.PropertiesContainer;

/**
 * Container for comparisons categories
 *
 * Created by vvedenin on 7/1/2016.
 */
@PropertiesContainer // class without getter and setter (see Properties in C#)
public class CompareCategoryIndex implements Comparable {
    public String knownCategory; // Category that mapping we already known
    public String findingCategory; // Category that mapping we need to find

    public static CompareCategoryIndex create(String knownCategory, String findingCategory) {
        CompareCategoryIndex result = new CompareCategoryIndex();
        result.findingCategory = findingCategory;
        result.knownCategory = knownCategory;
        return result;
    }

    @Override
    public String toString() {
        return findingCategory + " : " + knownCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompareCategoryIndex that = (CompareCategoryIndex) o;

        if (!knownCategory.equals(that.knownCategory)) return false;
        return findingCategory.equals(that.findingCategory);

    }

    @Override
    public int hashCode() {
        int result = knownCategory.hashCode();
        result = 31 * result + findingCategory.hashCode();
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
