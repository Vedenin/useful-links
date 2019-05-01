package com.github.vedenin.atoms.csv;

import au.com.bytecode.opencsv.CSVWriter;
import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Contract;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/19/2016.
 */
@Atom(CSVWriter.class)
@Contract("Should be used to write to CSV file")
public class CSVWriterAtom implements Closeable {
    private final CSVWriter csvWriter;

    @Contract("Write one line of CSV files")
    public void writeNext(String[] nextLine) {
        csvWriter.writeNext(nextLine);
    }

    @Override
    @Contract("Should be closed original object")
    public void close() throws IOException {
        csvWriter.close();
    }

    // -------------- Just boilerplate code for Atom -----------------
    @BoilerPlate
    private CSVWriterAtom(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    @BoilerPlate
    public static CSVWriterAtom getAtom(CSVWriter csvReader) {
        return new CSVWriterAtom(csvReader);
    }

    @BoilerPlate
    public static CSVWriterAtom create(String fileName, char separator) throws IOException {
        return getAtom(new CSVWriter(new FileWriter(fileName), separator));
    }

}
