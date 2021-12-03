package com.gillio.androic.compiler;

import com.gillio.androic.libs.exec;

import java.nio.file.Path;

public class apkSigner {

    Path apkSignerPath;

    public apkSigner(Path apkSignerPath) {
        this.apkSignerPath = apkSignerPath;
    }

    public String sign(Path apkPath, Path newApkPath, Path keyStorePath) {
        return exec.quickExec("dalvikvm -Xcompiler-option --compiler-filter=speed -cp " +
                apkSignerPath.toString() + " com.android.apksigner.ApkSignerTool sign --key " + keyStorePath.toString()
                + "/testkey.pk8 --cert " + keyStorePath.toString() + "/testkey.x509.pem --in " + apkPath.toString() +
                " --out " + newApkPath.toString());
    }
}

