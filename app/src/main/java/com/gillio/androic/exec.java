package com.gillio.androic;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class exec {

    public final ProcessBuilder mProcess = new ProcessBuilder();
    public final StringWriter mWriter = new StringWriter();

    public String Execute_cmd(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;
    }
    public void setCommands(ArrayList<String> cmds) {
        this.mProcess.command(cmds);
    }
    public String Execute_bin() throws IOException {
        Scanner scanner = null;
            scanner = new Scanner(this.mProcess.start().getErrorStream());

        while (scanner.hasNextLine()) {
                this.mWriter.append(scanner.nextLine());
                this.mWriter.append(System.lineSeparator());
            }
        return this.mWriter.toString();
    }
    public String getLog() {
        return this.mWriter.toString();
    }
}
