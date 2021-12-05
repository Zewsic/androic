package com.gillio.androic.compiler;


import android.util.Log;
import com.gillio.androic.libs.exec;
import com.gillio.androic.libs.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ecj {

    Path ecjPath;

    public ecj(Path ecjPath) {
        this.ecjPath = ecjPath;
    }

    public String compile(String memory, Path inPath, Path outPath, Path androidPath) {

        String filesList = "";
        utils util = new utils();
        util.getFiles(inPath);
        ArrayList<Path> all_files = util.files_list;

        for (Path filePath : all_files) {
            filesList += filePath + " ";
        }
        Log.i("file", filesList.toString());

        return exec.quickExec("dalvikvm -Xmx256m -Xcompiler-option --compiler-filter=speed -cp "+ ecjPath.toString() +
                " org.eclipse.jdt.internal.compiler.batch.Main " +
                "-proc:none -7 -cp "+androidPath.toString()+" -d " + outPath.toString() + "/class " + filesList);
     }


    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
