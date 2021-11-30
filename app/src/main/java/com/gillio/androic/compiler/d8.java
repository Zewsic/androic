package com.gillio.androic.compiler;

import java.io.IOException;
import java.nio.file.Path;
import com.gillio.androic.libs.exec;

public class d8 {

    Path d8Path;

    public d8(Path d82Path) {
        this.d8Path = d8Path;
    }

    public void compile(Path inPath, Path outPath, Path androidPath, String package_name) throws IOException {

        Runtime.getRuntime().exec("dalvikvm -Xcompiler-option —compiler-filter=speed " +
                "-Xmx256m -cp "+d8Path.toString()+" com.android.tools.r8.D8 --lib " + androidPath.toString() + " --output " +
                        outPath + "/dex " + inPath + "/class"+package_name.replace(".", "/")+"/*.class --release");

        //return exec.quickExec(d8Path.toString() + " --lib " + androidPath.toString() + " --output " +
        //        outPath + "/dex " + outPath + "/class"+package_name.replace(".", "/")+"/*.class --release");
    }
}
