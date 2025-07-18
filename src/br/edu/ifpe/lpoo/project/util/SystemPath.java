package br.edu.ifpe.lpoo.project.util;

import java.io.File;
import java.nio.file.Paths;

public class SystemPath {
    private static final String APP_NAME_DIR = "SistemaBibliotecaIFPE";
    private static final String COVERS_SUBDIR = "covers";


    public static String getAppBaseDataDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return System.getenv("LOCALAPPDATA") != null ? System.getenv("LOCALAPDATA") : System.getenv("APPDATA");
        } else if (os.contains("mac")) {
            return System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support";
        } else {
            return System.getProperty("user.home") + File.separator + ".local" + File.separator + "share";
        }
    }

    public static String getAppDirectory() {
        String appBaseDir = getAppBaseDataDirectory();
        File appDir = new File(appBaseDir, APP_NAME_DIR);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return appDir.getAbsolutePath();
    }

    public static String getCoversDirectory() {
        String appDir = getAppDirectory();
        File coversDir = new File(appDir, COVERS_SUBDIR);
        if (!coversDir.exists()) {
            coversDir.mkdirs();
        }
        return coversDir.getAbsolutePath();
    }

    public static String getCoverFilePath(String filename) {
        return Paths.get(getCoversDirectory(), filename).toString();
    }
}
