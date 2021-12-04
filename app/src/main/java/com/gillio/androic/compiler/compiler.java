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
    private String app_Path;
    private String package_name;
    private Path keyStorePath;

    private aapt2 _aapt2_;
    private d8 _d8_;
    private ecj _ecj_;
    private zipAlign _zipAlign_;
    private apkSigner _apkSigner_;

    public void ready(Path aapt2Path, Path ecjPath, Path zipAlignPath, Path apkSignerPath) {
        exec.quickExec("mkdir /storage/emulated/0/"+app_Path+"/projects");
        exec.quickExec("mkdir /storage/emulated/0/"+app_Path+"/projects/" + package_name);
        exec.quickExec("mkdir /storage/emulated/0/"+app_Path+"/projects/" + package_name + "/cache");
        exec.quickExec("mkdir /storage/emulated/0/"+app_Path+"/projects/" + package_name + "/cache/class");
        exec.quickExec("mkdir /storage/emulated/0/"+app_Path+"/projects/" + package_name + "/cache/gen");
        exec.quickExec("mkdir /storage/emulated/0/"+app_Path+"/projects/" + package_name + "/output");

        this.cache_path = Paths.get("/storage/emulated/0/"+app_Path+"/projects/" + package_name + "/cache");
        this.output_path = Paths.get("/storage/emulated/0/"+app_Path+"/projects/" + package_name + "/output");

        this._aapt2_ = new aapt2(aapt2Path);
        this._d8_ = new d8();
        this._ecj_ = new ecj(ecjPath);
        this._zipAlign_ = new zipAlign(zipAlignPath);
        this._apkSigner_ = new apkSigner(apkSignerPath);
    }

    public compiler(Path res_path, Path java_path, Path manifest_path, Path androidJar_path,
                    String app_Path, String package_name, Path keyStorePath) {
        this.res_path = res_path;
        this.java_path = java_path;
        this.manifest_path = manifest_path;
        this.androidJar_path = androidJar_path;
        this.package_name = package_name;
        this.keyStorePath = keyStorePath;
        this.app_Path = app_Path;
    }


    public void compile(TextView cl) throws IOException, CompilationFailedException {
        _aapt2_.compile(res_path, cache_path);
        _aapt2_.link(cache_path, cache_path, java_path, manifest_path, androidJar_path);
        cl.setText(_ecj_.compile("256", Paths.get(java_path.toString() + "/" + package_name.replace(".", "/")),
                cache_path, androidJar_path));
        _d8_.compile(cache_path, cache_path, cache_path, "com.zewsic.home");
        _aapt2_.add(Paths.get(cache_path.toString() + "/gen"), Paths.get(cache_path.toString() + "/classes.dex"));
        _aapt2_.pack(Paths.get(cache_path.toString() + "/gen"), Paths.get(cache_path.toString() + "/gen.apk"));
        _zipAlign_.align(Paths.get(cache_path.toString() + "/gen.apk"),
                Paths.get(cache_path.toString() + "/gen_aligned.apk"));
        _apkSigner_.sign(Paths.get(cache_path.toString() + "/gen_aligned.apk"),
                        Paths.get(output_path.toString() + "/gen_signed.apk"), keyStorePath);
    }
}










