package org.example.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

    public static String currentDir() {
        return System.getProperty("user.dir");
    }

    public static String dataDir() {
        return Paths.get(currentDir(), "data").toString();
    }

    public static Path path(String... paths) {
        return Paths.get(dataDir(), paths);
    }


}
