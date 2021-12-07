package com.gillio.androic.activites;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.android.tools.r8.CompilationFailedException;
import com.gillio.androic.R;
import com.gillio.androic.compiler.compiler;
import com.gillio.androic.compiler.d8;
import com.gillio.androic.libs.exec;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView cl;
    EditText co;
    Button start_compile;
    ArrayList<String> cmds;
    exec exa;
    File build_tools_folder;

    String java_path = "/";
    String res_path = "/";
    String manifest_path = "/";
    String androidJar_path = "/sdcard/.androic/build-tools/android.jar";
    String package_name = "com.zewsic.home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl = findViewById(R.id.cl);
        co = findViewById(R.id.co);
        start_compile = findViewById(R.id.compile_btn);
        cmds = new ArrayList<>();
        exa = new exec();
        build_tools_folder = new File("/data/data/com.gillio.androic/build-tools/");

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        if (checkSelfPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 2);
        }

        exec.quickExec("mkdir /storage/emulated/0/.androic");
        exec.quickExec("mkdir /storage/emulated/0/.androic/build-tools");
        exec.quickExec("mkdir /data/data/com.gillio.androic/build-tools");

        exec.quickExec("cp /storage/emulated/0/.androic/build-tools/aapt2 /data/data/com.gillio.androic/build-tools/aapt2");
        exec.quickExec("chmod 777 /data/data/com.gillio.androic/build-tools/aapt2");

        exec.quickExec("cp /storage/emulated/0/.androic/build-tools/zipAlign /data/data/com.gillio.androic/build-tools/zipAlign");
        exec.quickExec("chmod 777 /data/data/com.gillio.androic/build-tools/zipAlign");


        round(findViewById(R.id.Body), 90, "#1f1f1f");
        round(findViewById(R.id.get_m), 20, "#2f2f2f");
        round(findViewById(R.id.get_j), 20, "#2f2f2f");
        round(findViewById(R.id.get_r), 20, "#2f2f2f");
        round(findViewById(R.id.compile_btn), 20, "#2f2f2f");


    }

    public void c_btn(View view) throws IOException, CompilationFailedException {
        if (manifest_path == "/") {
            start_compile.setText("Manifest file not specified");
            return;
        }
        if (java_path == "/") {
            start_compile.setText("Java folder not specified");
            return;
        }
        if (res_path == "/") {
            start_compile.setText("Res folder not specified");
            return;
        }
        int id = (int)((Math.random()*((1000-1)+1))+1);
        String project_id = Integer.toString(id);
        start_compile.setText("Compiling, please wait...");

        compiler comp = new compiler(Paths.get(res_path), Paths.get(java_path),
                Paths.get(manifest_path), Paths.get(androidJar_path),
                ".androic",  project_id, Paths.get("/sdcard/.androic/build-tools"));
        comp.ready(Paths.get("/data/data/com.gillio.androic/build-tools/aapt2"), Paths.get("/sdcard/.androic/build-tools/ecj.jar"),
                Paths.get("/data/data/com.gillio.androic/build-tools/zipAlign"), Paths.get("/sdcard/.androic/build-tools/apkSigner.jar"));
        comp.compile(cl);

        start_compile.setText("OK! ID: "+project_id);
        //cl.setText(exec.quickExec(co.getText().toString()));

    }

    public void round(View _view, int _value, String _color){
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color));
        gd.setCornerRadius((int)_value);
        _view.setBackground(gd);
    }

    public void getJavaFolderPath(View view) {
            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(Intent.createChooser(i, "Выберите директорию java"), 1);
    }

    public void getResFolderPath(View view) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(i, "Выберите директорию res"), 2);
    }

    public void getManifestFile(View view) {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Выберите файл AndroidManifest.xml");
        startActivityForResult(chooseFile, 3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                java_path =  Paths.get(data.getData().toString()).toFile().getAbsolutePath().
                replace("%3A", "/").replace("%2F", "/").replace(
                        "/content:/com.android.externalstorage.documents/tree/primary", "/sdcard");
                break;
            case 2:
                res_path =  Paths.get(data.getData().toString()).toFile().getAbsolutePath().
                        replace("%3A", "/").replace("%2F", "/").replace(
                                "/content:/com.android.externalstorage.documents/tree/primary", "/sdcard");
                break;
            case 3:
                manifest_path = Paths.get(data.getData().toString()).toFile().getAbsolutePath().
                        replace("%3A", "/").replace("%2F", "/").replace(
                                "/content:/com.android.externalstorage.documents/document/primary", "/sdcard");
                break;
        }
    }


}