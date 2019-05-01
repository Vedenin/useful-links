package com.github.vedenin.atoms.csv;

import au.com.bytecode.opencsv.CSVReader;
import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Contract;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/19/2016.
 */
@Atom(CSVReader.class)
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
