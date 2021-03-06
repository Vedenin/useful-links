package com.github.vedenin.project_parser.storeresult;

import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.exceptions.StoreException;
import com.github.vedenin.atoms.csv.CSVReaderAtom;
import com.github.vedenin.atoms.csv.CSVWriterAtom;
import com.github.vedenin.atoms.io.ReflectUtilsAtom;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * CSVStoreManager - save parsing result to CSV
 *
 * Created by Slava Vedenin on 6/14/2016.
 */
public class CSVStoreManager implements StoreManager {
    @Override
    public void writeProjects(String name, Collection<ProjectContainer> lists) {
        saveLists(name, lists, ProjectContainer.class);
    }

    @Override
    public List<ProjectContainer> readProjects(String fileName) {
        return readLists(fileName, ProjectContainer.class);
    }

    private <T> List<T>  readLists(String fileName, Class<T> cls) {
        List<T> result = new ArrayList<>();
        try(CSVReaderAtom reader = CSVReaderAtom.create(fileName, '\t') ) {
            Map<String, Field> fields = getAllFieldsMap(cls);

            List<String[]> lines = reader.readAll();
            Map<Integer, Field> map = new HashMap<>(lines.get(0).length);
            for(String[] line:lines) {
                if(map.isEmpty()) {
                    int i = 0;
                    for(String header: line) {
                        map.put(i, fields.get(header.toLowerCase()));
                        i++;
                    }
                } else {
                    Constructor<T> constructor = cls.getConstructor();
                    T project = constructor.newInstance();
                    int i = 0;
                    for(String value: line) {
                        Field field = map.get(i);
                        if(value != null && !value.equals("null") && !value.isEmpty() && field != null) {
                            if(field.getType().equals(Integer.class)) {
                                field.set(project, Integer.parseInt(value));
                            } else if(field.getType().equals(Boolean.class)) {
                                field.set(project, "true".equals(value.toLowerCase()));
                            } else {
                                field.set(project, value);
                            }
                        }
                        i++;
                    }
                    result.add(project);
                }
            }

        } catch (IOException|ReflectiveOperationException exp) {
            throw new StoreException("CSVStoreManager.readProjects", exp);
        }
        return result;
    }

    private <T> Map<String, Field> getAllFieldsMap(Class<T> cls) {
        List<Field> fieldsList = ReflectUtilsAtom.getAllFieldsList(cls);
        Map<String, Field> fields = new HashMap<>(fieldsList.size());
        for(Field field: fieldsList) {
            fields.put(field.getName().toLowerCase(), field);
        }
        return fields;
    }

    private <T> void saveLists(String fileName, Collection<T> objects, Class<T> cls) {
        try (CSVWriterAtom writer = CSVWriterAtom.create(fileName, '\t')) {
            List<Field> fieldsList = ReflectUtilsAtom.getAllFieldsList(cls);
            writeHeaders(writer, fieldsList);
            writeBody(objects, writer, fieldsList);
        } catch (IllegalAccessException | IOException e) {
            throw new StoreException("CSVStoreManager.saveLists", e);
        }
    }

    private static <T> void writeBody(Collection<T> objects, CSVWriterAtom writer, List<Field> fieldsList)
            throws IllegalAccessException {
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
        return value == null? "null": value.toString().replaceAll("\"", " ").replaceAll("'", " ").replaceAll("  ", " ").trim();
    }

    private static void writeHeaders(CSVWriterAtom writer, List<Field> fieldsList) {
        String[] headers = new String[fieldsList.size()];
        int i = 0;
        for(Field field: fieldsList) {
            headers[i++] = field.getName();
        }
        writer.writeNext(headers);
    }

}
