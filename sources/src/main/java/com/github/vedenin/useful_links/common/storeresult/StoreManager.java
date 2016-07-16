package com.github.vedenin.useful_links.common.storeresult;

import com.github.vedenin.useful_links.common.containers.ProjectContainer;

import java.util.Collection;

/**
 * Interface to storeresult parsing results to database or files
 *
 * Created by vvedenin on 6/14/2016.
 */
public interface StoreManager {
    void writeProjects(String name, Collection<ProjectContainer> lists);
    Collection<ProjectContainer> readProjects(String name);
}
