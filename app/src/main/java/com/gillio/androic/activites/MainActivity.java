package com.gillio.androic.activites;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
    ArrayList<String> cmds;
    exec exa;
    File build_tools_folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl = findViewById(R.id.cl);
        co = findViewById(R.id.co);
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

        exec.quickExec("cp /storage/emulated/0/.androic/build-tools/compiler_libs.jar /data/data/com.gillio.androic/build-tools/compiler_libs.jar");
        exec.quickExec("chmod 777 /data/data/com.gillio.androic/build-tools/compiler_libs.jar");


    }

    public void c_btn(View view) throws IOException, CompilationFailedException {
        //cmds.clear();
        //cmds = new ArrayList<>(Arrays.asList(co.getText().toString().split(" ")));
        //exa.setCommands(cmds);
        //cl.setText(exa.execute());

        //aapt2 aapt = new aapt2(Paths.get("/data/data/com.gillio.androic/build-tools/aapt2"));
        //aapt.compile(Paths.get("/sdcard/testApp/res"), Paths.get("/sdcard/testApp/output"));
        //aapt.link(Paths.get("/sdcard/testApp/output/compiled_res.zip"), Paths.get("/sdcard/testApp/output"),
        //        Paths.get("/sdcard/testApp/AndroidManifest.xml"), Paths.get("/sdcard/testApp/android.jar"));

        compiler comp = new compiler(Paths.get("/sdcard/testApp/res"), Paths.get("/sdcard/testApp/java"),
                Paths.get("/sdcard/testApp/AndroidManifest.xml"), Paths.get("/sdcard/.androic/build-tools/android.jar"),
                Paths.get("/sdcard/testApp/cache"), Paths.get("/sdcard/testApp/output"), "com.zewsic.home");

        comp.ready(Paths.get("/data/data/com.gillio.androic/build-tools/aapt2"), Paths.get("/sdcard/.androic/build-tools/ecj.jar"));
        comp.compile(cl);

        //d8 dd = new d8(Paths.get("/sdcard/.androic/build-tools/d8.jar"));
        //cl.setText(dd.compile(Paths.get("/sdcard/testApp/cache"), Paths.get("/sdcard/testApp/cache"), Paths.get("/sdcard/.androic/build-tools/android.jar"),
        //       "com.zewsic.home"));

        //cl.setText(exec.quickExec(co.getText().toString()));

    }


}