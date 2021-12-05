package com.gillio.androic.libs;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class utils {

    public ArrayList<Path> files_list = new ArrayList<Path>();

    public static void copyAssets(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                String outDir = "/storage/emulated/0/.androic/" ;
                File outFile = new File(outDir, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
        }
    }
    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    public void getFiles(Path path) {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path pathq : files) {
                System.out.println("Checking: " + pathq.toString());
                File file2 = pathq.toFile();
                if (file2.isFile()) {
                    files_list.add(pathq);
                } else {
                    getFiles(Paths.get(file2.toString() + File.separator));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
