package com.gillio.androic.compiler;

import android.util.Log;
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
import com.gillio.androic.libs.utils;


public class d8 {


    public String compile(Path inPath, Path outPath, Path androidPath) throws IOException, CompilationFailedException {

        String filesList = "";
        utils util = new utils();
        util.getFiles(Paths.get(inPath.toString() + "/class"));
        ArrayList<Path> all_files = util.files_list;

            D8.run(D8Command.builder()
                    .setMode(CompilationMode.RELEASE)
                    .setIntermediate(true)
                    .setMinApiLevel(21)
                    .setOutput(Paths.get(outPath.toString()), OutputMode.DexIndexed)
                    .addProgramFiles(all_files)
                    .build());

        return "";
    }

}
