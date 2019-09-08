package voice.commands;

import Core.Issue;
import Core.VoiceCommand;

import java.io.IOException;

public class Terminal implements VoiceCommand {
    private Process process;

    @Override
    public void execute() {

        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("cmd /k start cmd");
            } else  if (os.contains("mac")){
                Runtime.getRuntime().exec("/bin/bash -c echo terminal launched");
            } else if(os.contains("nix") || os.contains("nux")){
                String command= "/usr/bin/xterm";
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(command);
            } else {
                Issue.addIssue("failed to execute T_T voice!");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Issue.addIssue("failed to execute terminal command");
        }
    }

    @Override
    public void close() {
        try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        process.destroyForcibly();
    }
}
