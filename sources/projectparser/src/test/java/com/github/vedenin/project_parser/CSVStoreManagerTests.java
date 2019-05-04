package com.github.vedenin.project_parser;

import com.github.vedenin.project_parser.resources.Resources;
import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.storeresult.CSVStoreManager;
import com.github.vedenin.project_parser.storeresult.StoreManager;
import com.github.vedenin.project_parser.crawlers.old.JavaUsefulProjects;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Slava Vedenin on 6/16/2016.
 */
public class CSVStoreManagerTests {
    @Test
    @Ignore
    public void testCSVTest() throws IOException {
        URL url = this.getClass().getResource("/useful-java-links.html");
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
        Map<String, ProjectContainer> projects = thisCls.getProjects(url.toString());
        assertEquals(732, projects.size());
        StoreManager storeManager = new CSVStoreManager();
        storeManager.writeProjects("exports/useful-java-links.csv", projects.values());
    }

    @Test
    public void readUsefulJavaLinks() {
        Resources resources = new Resources();
        StoreManager storeManager = new CSVStoreManager();
        String path = resources.getResourcePath("/exports/useful-java-links.csv");
        List<ProjectContainer> results = storeManager.readProjects(path);
        assertEquals(728, results.size());
    }
}
