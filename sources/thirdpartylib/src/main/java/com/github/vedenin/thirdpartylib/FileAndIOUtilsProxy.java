package com.github.vedenin.thirdpartylib;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * Proxy object for static FileUtils methods
 *
 * Created by vvedenin on 12/16/2016.
 */
public class FileAndIOUtilsProxy {
    private static final Charset FILE_CODE_PAGE = Charsets.UTF_8;

    public static Collection<File> listFiles(final File directory) {
        return FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }

    public static void writeStringToFile(final File file, final String data) throws IOException {
        FileUtils.writeStringToFile(file, data, FILE_CODE_PAGE);
    }

    public static String readFileToString(final File file) throws IOException {
        return FileUtils.readFileToString(file, FILE_CODE_PAGE);
    }

    public static List<String> readLines(final File file) throws IOException {
        return Files.readLines(file, FILE_CODE_PAGE);
    }

    public static String readIOtoString(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream, FILE_CODE_PAGE);
    }
}
