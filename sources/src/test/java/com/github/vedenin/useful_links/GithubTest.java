package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.common.Resources;
import com.github.vedenin.useful_links.common.containers.GithubInfoContainer;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.DownloadProjects;
import com.github.vedenin.useful_links.crawlers.GithubAndPageStatistics;
import com.github.vedenin.useful_links.crawlers.impl.DownloadProjectsImpl;
import com.github.vedenin.useful_links.crawlers.impl.GithubAndPageStatisticsImpl;
import com.github.vedenin.useful_links.store_result.CSVStoreManager;
import com.github.vedenin.useful_links.store_result.StoreManager;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.github.vedenin.useful_links.common.utils.DownloadUtils.getPage;

/**
 * Test github's
 * <p>
 * Created by vvedenin on 7/12/2016.
 */
public class GithubTest {
    //@Test
    public void testGithubStatistics() {
        GithubAndPageStatisticsImpl thisCls = new GithubAndPageStatisticsImpl();
        String url = "https://github.com/Vedenin/useful-java-links";
        GithubInfoContainer result = thisCls.getGithubInfo(getPage(url), url);
        System.out.println(result);
    }

    //@Test
    public void testGithubStaticticsNew() {
        Resources resources = new Resources();
        DownloadProjects downloadProjects = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        GithubAndPageStatistics githubStatistics = new GithubAndPageStatisticsImpl();
        URL url = this.getClass().getResource("/useful-java-links.html");
        Map<String, ProjectContainer> projects = downloadProjects.getProjects(url.toString());
        Map<String, ProjectContainer> result = githubStatistics.getProjectWithGithubInfo(projects);
        result.values().stream().forEach(System.out::println);

        StoreManager storeManager = new CSVStoreManager();
        storeManager.writeProjects("useful-java-links.csv", result.values());
    }

    @Test
    public void testAwesomeLists() {
        Resources resources = new Resources();
        DownloadProjects downloadProjects = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        GithubAndPageStatistics githubStatistics = new GithubAndPageStatisticsImpl();
        Map<String, ProjectContainer> projects = downloadProjects.getProjects("https://github.com/akullpp/awesome-java");
        Map<String, ProjectContainer> result = githubStatistics.getProjectWithGithubInfo(projects);
        result.values().stream().forEach(System.out::println);

        StoreManager storeManager = new CSVStoreManager();
        storeManager.writeProjects("awesome-java.csv", result.values());
    }

    //@Test
    public void getProjectsTest() {
        Resources resources = new Resources();
        DownloadProjects thisCls = new DownloadProjectsImpl(resources.getNonProjectHeaders(), resources.getNonProjectMainHeaders());
        List<String> awesomeLists = resources.getListFromConfig("/awesome-lists/lists.txt");

        for(String awesomeList: awesomeLists) {

        }
    }
}
