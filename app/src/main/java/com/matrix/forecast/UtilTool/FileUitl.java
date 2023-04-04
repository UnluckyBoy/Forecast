package com.matrix.forecast.UtilTool;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.FileReader;
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

    /***
     * 读取文件
     */
    public static String readFileRecursion(String fileNamePath) throws IOException {
        BufferedReader bf= new BufferedReader(new FileReader(fileNamePath));
        String str_temp="",str_result="";
        while ((str_temp = bf.readLine())!= null) // 判断最后一行不存在，为空结束循环
        {
            str_result+=str_temp;
            //System.out.println(str_temp);//原样输出读到的内容
        }
        bf.close();

        return str_result;
    }
}
