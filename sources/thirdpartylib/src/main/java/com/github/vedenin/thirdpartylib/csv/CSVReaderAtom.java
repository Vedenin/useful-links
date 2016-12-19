package com.github.vedenin.thirdpartylib.csv;

import au.com.bytecode.opencsv.CSVReader;
import com.github.vedenin.thirdpartylib.annotations.Atom;
import com.github.vedenin.thirdpartylib.annotations.BoilerPlate;
import com.github.vedenin.thirdpartylib.annotations.Contract;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by vvedenin on 12/19/2016.
 */
@Atom
@Contract("Should be used to read from CSV file")
public class CSVReaderAtom implements Closeable {
    private final CSVReader csvReader;

    @Override
    @Contract("Should be closed original object")
    public void close() throws IOException {
        csvReader.close();
    }

    @Contract("Return all csv lines as list (and it's values as array of Strings)")
    public List<String[]> readAll() throws IOException {
        return csvReader.readAll();
    }

    // -------------- Just boilerplate code for Atom -----------------
    @BoilerPlate
    private CSVReaderAtom(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    @BoilerPlate
    public static CSVReaderAtom getAtom(CSVReader csvReader) {
        return new CSVReaderAtom(csvReader);
    }

    @BoilerPlate
    public static CSVReaderAtom create(String fileName, char separator) throws FileNotFoundException {
        return getAtom(new CSVReader(new FileReader(fileName), separator));
    }
}
