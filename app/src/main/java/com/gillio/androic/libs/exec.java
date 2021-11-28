package com.gillio.androic.libs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class exec {

    private final ProcessBuilder mProcess = new ProcessBuilder();
    private final StringWriter mWriter = new StringWriter();

    public void setCommands(ArrayList<String> arrayList) {
        mProcess.command(arrayList);
    }
    public void setCommands(String cmd) {
        mProcess.command(new ArrayList<String>(Arrays.asList(cmd.split(" "))));
    }

    public String execute() {
        try {
            Scanner scanner = new Scanner(mProcess.start().getErrorStream());
            while (scanner.hasNextLine()) {
                mWriter.append(scanner.nextLine());
                mWriter.append(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(mWriter));
        }
        return mWriter.toString();
    }


    public String getLog() {
        return mWriter.toString();
    }

    static public String quickExec(String cmd){
        exec obj = new exec();
        obj.setCommands(new ArrayList<String>(Arrays.asList(cmd.split(" "))));
        return obj.execute();
    }
}