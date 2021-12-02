package com.gillio.androic.compiler;

import android.widget.EditText;
import android.widget.TextView;
import com.android.tools.r8.CompilationFailedException;
import com.gillio.androic.libs.exec;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private ecj _ecj_;

    public void ready(Path aapt2Path, Path ecjPath, Path aaptPath) {
        this._aapt2_ = new aapt2(aapt2Path);
        this._d8_ = new d8();
        this._ecj_ = new ecj(ecjPath);
    }

    public compiler(Path res_path, Path java_path, Path manifest_path, Path androidJar_path,
                    Path cache_path, Path output_path, String package_name) {
        this.res_path = res_path;
        this.java_path = java_path;
        this.manifest_path = manifest_path;
        this.androidJar_path = androidJar_path;
        this.cache_path = cache_path;
        this.output_path = output_path;
        this.package_name = package_name;
    }


    public void compile(TextView cl) throws IOException, CompilationFailedException {
        cl.setText("aapt2 is compiling...");
        _aapt2_.compile(res_path, cache_path);
        cl.setText("aapt2 is linking...");
        _aapt2_.link(cache_path, cache_path, manifest_path, androidJar_path);
        cl.setText("ecj is running...");
        _ecj_.compile("256", Paths.get(java_path.toString() + "/" + package_name.replace(".", "/")),
                cache_path, androidJar_path);
        cl.setText("d8 is running...");
        _d8_.compile(cache_path, cache_path, cache_path, "com.zewsic.home");
        cl.setText("aapt2 is adding files...");
        _aapt2_.add(Paths.get(cache_path.toString() + "/gen"), Paths.get(cache_path.toString() + "/classes.dex"));
        cl.setText("aapt2 is packing apk...");
        _aapt2_.pack(Paths.get(cache_path.toString() + "/gen"), Paths.get(cache_path.toString() + "/gen.apk"));
    }
}










