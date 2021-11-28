package com.gillio.androic.compiler;

import java.nio.file.Path;
import com.gillio.androic.libs.exec;

public class d8 {

    Path d8Path;

    public d8(Path aapt2Path) {
        this.d8Path = aapt2Path;
    }

    public String compile(Path inPath, Path outPath, Path androidPath, String package_name) {

        return exec.quickExec(d8Path.toString() + " --lib " + androidPath.toString() + " --output " +
                outPath + "/dex " + outPath + "/classes/*.class --release");
    }
}
