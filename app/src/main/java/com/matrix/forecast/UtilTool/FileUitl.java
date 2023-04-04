package com.matrix.forecast.UtilTool;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUitl {

    /***
     * 保存到文件
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createFileRecursion(String fileName, Integer height) throws IOException {
        Path path = Paths.get(fileName);
        if (Files.exists(path)) {
            return;
        }
        if (Files.exists(path.getParent())) {
            if (height == 0) {
                Files.createFile(path);
            } else {
                Files.createDirectory(path);
            }
        } else {
            createFileRecursion(path.getParent().toString(), height + 1);
            createFileRecursion(fileName, height);
        }
    }
}
