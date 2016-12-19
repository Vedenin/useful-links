package com.github.vedenin.thirdpartylib.csv;

import au.com.bytecode.opencsv.CSVWriter;
import com.github.vedenin.thirdpartylib.annotations.Atom;
import com.github.vedenin.thirdpartylib.annotations.BoilerPlate;
import com.github.vedenin.thirdpartylib.annotations.Contract;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by vvedenin on 12/19/2016.
 */
@Atom
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
    public static CSVWriterAtom getProxy(CSVWriter csvReader) {
        return new CSVWriterAtom(csvReader);
    }

    @BoilerPlate
    public static CSVWriterAtom create(String fileName, char separator) throws IOException {
        return getProxy(new CSVWriter(new FileWriter(fileName), separator));
    }

}
