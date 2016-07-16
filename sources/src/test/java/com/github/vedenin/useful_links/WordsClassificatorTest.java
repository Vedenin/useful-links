package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.classificator.WordsClassificator;
import com.github.vedenin.useful_links.common.Resources;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import org.junit.Test;
import com.github.vedenin.useful_links.common.storeresult.StoreManager;
import com.github.vedenin.useful_links.common.storeresult.CSVStoreManager;

import java.util.Collection;
import java.util.List;

/**
 * Created by vedenin on 16.07.16.
 */
public class WordsClassificatorTest {
    @Test
    public void test() {
        WordsClassificator wordsClassificator = new WordsClassificator();
        Resources resources = new Resources();
        StoreManager storeManager = new CSVStoreManager();
        List<String> files = resources.getFiles("/exports");
        for(String file: files) {
            Collection<ProjectContainer> projects = storeManager.readProjects(file);
        }
    }
}
