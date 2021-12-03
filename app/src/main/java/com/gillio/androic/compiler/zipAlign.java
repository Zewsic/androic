package com.gillio.androic.compiler;

import com.gillio.androic.libs.exec;

import java.nio.file.Path;

public class zipAlign {

    Path zipAlignPath;

    public zipAlign(Path zipAlignPath) {
        this.zipAlignPath = zipAlignPath;
    }

    public String align(Path apkPath, Path newApkPath) {
        return exec.quickExec(zipAlignPath.toString() + " -v 4 " +
                apkPath.toString() + " " + newApkPath.toString());
    }
}

