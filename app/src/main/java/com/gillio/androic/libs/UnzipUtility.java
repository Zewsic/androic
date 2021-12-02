package com.gillio.androic.libs;

import android.util.Log;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 * @author www.codejava.net
 *
 */
public class UnzipUtility {

    private String _zipFile;
    private String _location;

    public void DecompressFast(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }

    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    BufferedOutputStream bufout = new BufferedOutputStream(fout);
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while ((read = zin.read(buffer)) != -1) {
                        bufout.write(buffer, 0, read);
                    }




                    bufout.close();

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();


            Log.d("Unzip", "Unzipping complete. path :  " +_location );
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);

            Log.d("Unzip", "Unzipping failed");
        }

    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }
}