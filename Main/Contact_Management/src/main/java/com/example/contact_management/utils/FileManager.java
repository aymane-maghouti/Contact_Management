package com.example.contact_management.utils;

import java.io.File;

public class FileManager {
    public FileManager() {
    }

    public static boolean fileExists(String filePathString) {
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }
}
