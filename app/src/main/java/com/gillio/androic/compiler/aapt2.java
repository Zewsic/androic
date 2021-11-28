package com.gillio.androic.compiler;

import java.nio.file.Path;
import com.gillio.androic.libs.exec;

public class aapt2 {

    Path aapt2Path;

    public aapt2(Path aapt2Path) {
        this.aapt2Path = aapt2Path;
    }

    public String compile(Path inPath, Path outPath) {
        return exec.quickExec(aapt2Path.toString() + " compile --dir " +
                inPath.toString() + " -o " + outPath.toString() + "/compiled_res.zip -v");
    }

    public String link(Path inPath, Path outPath, Path manifestPath, Path androidPath) {
        return exec.quickExec(aapt2Path.toString() + " link " + inPath.toString() + "/compiled_res.zip -o " +
                outPath.toString() + "/gen.apk -I " + androidPath.toString() + " --manifest " +
                manifestPath.toString() + " --min-sdk-version 21 --target-sdk-version 26 --java " +
                outPath.toString() + "/../java -v");
    }
}
