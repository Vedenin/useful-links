package com.github.vedenin.useful_links.container;

import com.github.vedenin.useful_links.annotation.PropertiesContainer;

/**
 * Returns information about github's project info
 *
 * Created by vvedenin on 5/11/2016.
 */
@PropertiesContainer // class without getter and setter (see Properties in C#)
public class GithubInfo {
    public String url;
    public Integer stars;
    public Integer watchs;
    public Integer forks;

    public static GithubInfo create() {
        return new GithubInfo();
    }

    @Override
    public String toString() {
        return "{" +
                "url=" + url +
                ", stars=" + stars +
                ", watchs=" + watchs +
                ", forks=" + forks +
                '}';
    }
}
