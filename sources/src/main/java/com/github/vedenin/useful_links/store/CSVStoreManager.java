package com.github.vedenin.useful_links.store;

import au.com.bytecode.opencsv.CSVWriter;
import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import com.github.vedenin.useful_links.common.utils.exceptions.StoreException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * CSVStoreManager - save parsing result to CSV
 *
 * Created by vvedenin on 6/14/2016.
 */
public class CSVStoreManager implements StoreManager {
    @Override
    public void writeProjects(String fileName, Collection<ProjectContainer> lists) {
        saveLists(fileName, lists, ProjectContainer.class);
    }

    @Override
    public Collection<ProjectContainer> readProjects(String fileName) {
        return null;
    }

    private <T> void saveLists(String fileName, Collection<T> objects, Class<T> cls) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName), '\t')) {
            List<Field> fieldsList = FieldUtils.getAllFieldsList(cls);
            writeHeaders(writer, fieldsList);
            writeBody(objects, writer, fieldsList);
        } catch (IllegalAccessException | IOException e) {
            throw new StoreException("CSVStoreManager.saveLists", e);
        }
    }

    private static <T> void writeBody(Collection<T> objects, CSVWriter writer, List<Field> fieldsList) throws IllegalAccessException {
        for(T container: objects) {
            int i = 0;
            String[] row = new String[fieldsList.size()];
            for(Field field: fieldsList) {
                row[i++] = toString(field, container);
            }
            writer.writeNext(row);
        }
    }

    private static String toString(Field field, Object object) throws IllegalAccessException {
        Object value = field.get(object);
        return value == null? "null": value.toString();
    }
    private static void writeHeaders(CSVWriter writer, List<Field> fieldsList) {
        String[] headers = new String[fieldsList.size()];
        int i = 0;
        for(Field field: fieldsList) {
            headers[i++] = field.getName();
        }
        writer.writeNext(headers);
    }

}
