package com.gillio.androic.compiler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public String link(Path inPath, Path outPath, Path java_path, Path manifestPath, Path androidPath) {
        return exec.quickExec(aapt2Path.toString() + " link " + inPath.toString() + "/compiled_res.zip -o " +
                outPath.toString() + "/gen -I " + androidPath.toString() + " --manifest " +
                manifestPath.toString() + " --min-sdk-version 21 --target-sdk-version 26 --java " +
                java_path.toString() + " --output-to-dir -v");
    }

    public String add(Path genFolder, Path file) {
        return exec.quickExec("cp " + file.toString() + " " + genFolder.toString() + "/" +
                file.getFileName());
    }

    public String pack(Path genFolder, Path outputFile) {
        Path p = null;
        try {
            p = Files.createFile(Paths.get(outputFile.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path pp = Paths.get(genFolder.toString());
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p));
             Stream<Path> paths = Files.walk(pp)) {
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
