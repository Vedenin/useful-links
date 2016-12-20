package com.github.vedenin.project_parser.storeresult;

import com.github.vedenin.project_parser.containers.ProjectContainer;

import java.util.Collection;
import java.util.List;

/**
 * Interface to storeresult parsing results to database or files
 *
 * Created by Slava Vedenin on 6/14/2016.
 */
public interface StoreManager {
    void writeProjects(String name, Collection<ProjectContainer> lists);
    List<ProjectContainer> readProjects(String name);
}
