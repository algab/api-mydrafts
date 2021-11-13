package br.com.mydrafts.apimydrafts.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class TestUtil {

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(String.format("src/test/resources%s", file))));
    }

}
