package voice.commands;

import Core.VoiceCommand;

import javax.swing.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class Browser implements VoiceCommand {
   private Process p;


    @Override
    public void execute()  {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime rt = Runtime.getRuntime();
                String url = "http://google.com";
               p = rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")){
                Runtime rt = Runtime.getRuntime();
                String url = "http://google.com";
               p = rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")){
                Runtime rt = Runtime.getRuntime();
                String url = "http://google.com";

                String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                        "netscape", "opera", "links", "lynx" };

                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    if(i == 0)
                        cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
                    else
                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                // If the first didn't work, try the next browser and so on

               p = rt.exec(new String[] { "sh", "-c", cmd.toString() });
            } else {
                JOptionPane.showMessageDialog(null,"Error OS version unsupported","OS not supported",JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.out.println(p + " was destroyed");
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("taskkill /f " + p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
