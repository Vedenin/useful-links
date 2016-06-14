package com.github.vedenin.useful_links.store;

import com.github.vedenin.useful_links.annotations.PropertiesContainer;

import java.util.List;

/**
 * Interface to store parsing results to database or files
 *
 * Created by vvedenin on 6/14/2016.
 */
public interface StoreManager {
    void writeProjects(List<PropertiesContainer> lists);
    List<PropertiesContainer> readProjects();
}
