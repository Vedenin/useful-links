package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.container.ProjectContainer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.github.vedenin.useful_links.utils.DownloadUtils.USER_AGENT;
import static com.github.vedenin.useful_links.utils.DownloadUtils.isHeader;

/**
 * Test for Download list of project
 *
 * Created by vedenin on 07.04.16.
 */
public class GithubDownloadTests {

    private static void testHtmlParser(String url) throws Exception {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).timeout(30000).get();
        Elements div = doc.select("#readme");

        //printElements(div);
        work(div);
    }

    private static void printElements(Elements children) {
        for(Element child: children) {
            if(!child.text().isEmpty()) {
                System.out.print(child.tag().getName() + " : ");
                System.out.println(child.text());
            }
            printElements(child.children());
        }
    }


    private static List<ProjectContainer> work(Elements elements) {
        List<ProjectContainer> result = new ArrayList<>(elements.size());
        String currentCategory = null;
        for(Element element: elements) {
            Tag tag = element.tag();
            if(isHeader(tag)) {
                currentCategory = element.text();
                System.out.println(currentCategory);
            }
            work(element.children());
        }
        return result;
    }

    public static void main(String[] s) throws Exception {
        testHtmlParser("https://github.com/Vedenin/useful-java-links/blob/master/readme.md");
    }

}
