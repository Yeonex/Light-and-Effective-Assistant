package Core;



import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Updater {

    private static URL updateUrl;

    static {
        try {
            updateUrl = new URL("http://ianzakharov.com/update.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static InputStream ver;
    private static BufferedReader bf;
    private static String currentVersion;
    private static final AlertDialog ad = new AlertDialog();
    private static boolean updatePending = false;

    public static void getVersion(String version) throws IOException{
        final JPanel panel = new JPanel();
        currentVersion = version;
        if(currentVersion == null || version.equals("")){
            JOptionPane.showMessageDialog(panel,"Version Can't be NULL/Lower/Empty \n If problem persist contact support!","Critical Error 4x01",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        System.out.println("Current Version: " + currentVersion + " update class");

    }

    public static void checkForUpdate() throws  IOException{
        System.out.println("Checking for update @ " + new java.util.Date());
        ver = updateUrl.openStream();
        bf = new BufferedReader(new InputStreamReader(ver, "UTF-8"));
        String line;
        while ((line = bf.readLine()) !=null){
            if(line.equals(currentVersion)){
                updatePending = false;
                System.out.println("System Up 2 Date");
            }  else {
                //TODO write updater code when version become available!
                updatePending = true;
                System.out.println("Code will be written here");
        }
        }

    }

    public static boolean getUpdateStatus(){
        return updatePending;
    }


}
