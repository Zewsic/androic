package com.gillio.androic.compiler;


import com.gillio.androic.libs.exec;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ecj {

    Path ecjPath;

    public ecj(Path ecjPath) {
        this.ecjPath = ecjPath;
    }

    public String compile(String memory, Path inPath, Path outPath, Path androidPath) {

        String filesList = "";
        for ( File file : new File(inPath.toString()).listFiles() ){
            if ( file.isFile() )
                //if (getFileExtension(file) == "java") {
                    filesList += file.getAbsolutePath() + " ";
                //}
        }
        //return filesList;
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
