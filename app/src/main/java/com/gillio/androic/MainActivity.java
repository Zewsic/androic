package com.gillio.androic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.wshunli.assets.CopyAssets;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
        cmds = new ArrayList<String>();
        exa = new exec();
        build_tools_folder = new File("/data/data/com.gillio.androic/build-tools/");

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        if (checkSelfPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 2);
        }

        exec.quickExec("mkdir /storage/emulated/0/.androic");
        CopyAssets.with(this).to("/storage/emulated/0/.androic/").copy();
        exec.quickExec("cp /storage/emulated/0/.androic/build-tools/ /data/data/com.gillio.androic/build-tools/");


    }


    public void c_btn(View view) {
        cmds.clear();
        cmds = new ArrayList<String>(Arrays.asList(co.getText().toString().split(" ")));
        exa.setCommands(cmds);
        cl.setText(exa.execute());
    }


}