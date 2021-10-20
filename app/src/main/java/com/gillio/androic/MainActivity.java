package com.gillio.androic;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
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

        TextView cl = findViewById(R.id.cl);;
        EditText co = findViewById(R.id.co);
        cmds = new ArrayList<String>();
        exec exa = new exec();
        build_tools_folder = new File(this.getFilesDir(), "build-tools");
    }


    public void c_btn(View view) {

            cmds.add(new File(this.getFilesDir(), "build-tools").getAbsolutePath());
            cmds.add("--help");
            exa.setCommands(cmds);
            exa.Execute_bin();
            cl.setText(exa.getLog());
        }


}