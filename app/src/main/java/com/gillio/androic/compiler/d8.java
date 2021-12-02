package com.gillio.androic.compiler;

import com.gillio.androic.libs.exec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;


public class d8 {


    public String compile(Path inPath, Path outPath, Path androidPath, String package_name) throws IOException, CompilationFailedException {

        //return exec.quickExec("dalvikvm -Xcompiler-option -compiler-filter=speed " +
        //        "-Xmx256m -cp /data/data/com.gillio.androic/build-tools/compiler_libs.jar com.android.tools.r8.D8 --lib " + androidPath.toString() + " --output " +
        //        outPath + "/dex " + inPath + "/class/"+package_name.replace(".", "/")+"/*.class --release");

        //return exec.quickExec(d8Path.toString() + " --lib " + androidPath.toString() + " --output " +
        //        outPath + "/dex " + outPath + "/class"+package_name.replace(".", "/")+"/*.class --release");

        Collection<Path> programFiles = new ArrayList<>();
        for ( File file : new File(inPath.toString() + "/class/"+package_name.replace(".", "/") + "/").listFiles() ){
            if ( file.isFile() )
                programFiles.add(file.toPath());
        }
//
            D8.run(D8Command.builder()
                    .setMode(CompilationMode.RELEASE)
                    .setIntermediate(true)
                    .setMinApiLevel(21)
                    .setOutput(Paths.get(outPath.toString()), OutputMode.DexIndexed)
                    .addProgramFiles(programFiles)
                    .build());

        return "";
    }

}
