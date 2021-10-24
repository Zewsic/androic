package com.gillio.androic;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.wshunli.assets.CopyAssets;

import java.io.File;
import java.io.IOException;
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

        cl = findViewById(R.id.cl);;
        co = findViewById(R.id.co);
        cmds = new ArrayList<String>();
        exa = new exec();
        build_tools_folder = new File("/storage/emulated/0/androic/", "build-tools");
    }


    public void c_btn(View view) throws IOException {

        cmds.add("/system/bin/ls");
        cmds.add("/storage/emulated/0/");
        exa.setCommands(cmds);
        cl.setText(exa.Execute_bin());

        //CopyAssets.with(this)
        //        .to("/storage/emulated/0/androic/")
        //        .copy();
        }


}