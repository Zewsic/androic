package com.gillio.androic;

import java.nio.file.Path;

public class compiler {

    private Path res_path;
    private Path java_path;
    private Path manifest_path;

    public void init(Path res_path, Path java_path, Path manifest_path){
        this.res_path = res_path;
        this.java_path = java_path;
        this.manifest_path = manifest_path;
    }



}
