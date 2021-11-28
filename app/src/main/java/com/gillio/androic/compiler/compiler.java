package com.gillio.androic.compiler;

import java.io.IOException;
import java.nio.file.Path;

public class compiler {

    private Path res_path;
    private Path java_path;
    private Path manifest_path;
    private Path androidJar_path;
    private Path cache_path;
    private Path output_path;
    private String package_name;

    private aapt2 _aapt2_;
    private d8 _d8_;

    public void ready(Path aapt2Path) {
        this._aapt2_ = new aapt2(aapt2Path);
    }

    public compiler(Path res_path, Path java_path, Path manifest_path, Path androidJar_path) {
        this.res_path = res_path;
        this.java_path = java_path;
        this.manifest_path = manifest_path;
        this.androidJar_path = androidJar_path;
    }


    public void compile() throws IOException {
        //_aapt2_.compile(res_path, cache_path);
        //_aapt2_.link(cache_path, cache_path, manifest_path, androidJar_path);
        //_d8_.compile()


        String arguments = ""; // The arguments that we want to put
        String command =
                "dalvikvm -Xmx256m -Xcompiler-option --compiler-filter=speed -cp /sdcard/testApp/ecj.jar org.eclipse.jdt.internal.compiler.batch.Main -proc:none -7 -cp /sdcard/testApp/android.jar " +
                        "-java_ver 1.7 -nowarn -d /sdcard/testApp/output/javac/ /sdcard/testApp/java/com.zewsic.home/*.java";

        Runtime.getRuntime().exec(command);
    }
}










