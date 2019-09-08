package Core;

import UI.MainScene;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.prefs.Preferences;

public class MobileSyncComponent {

    static  Socket s;
    static ServerSocket ss;
    static InputStreamReader isr;
    static BufferedReader br;
    static String msg;
    static String ip;
    static Boolean isconnected = false;
    static Preferences preferences = Preferences.userNodeForPackage(MainScene.class);
    static final String assID = preferences.get("LEA_LOCAL_MCHN_ID","");
    static String phoneSocket;
    static String myAddress;


    public static void StartSyncComponet() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            myAddress = inetAddress.toString();
        } catch (Exception e){
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Input IP: " + myAddress);
        System.out.println("Machnine match id :" + assID);
        Thread thread = new Thread() {
            public void run() {
                try{
                    ss = new ServerSocket(27015);
                    while (true){
                        s = ss.accept();
                        s.setKeepAlive(true);
                        System.out.println("Got mobile connection");
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        String data = dis.readUTF();
                        System.out.println("Data: " + data);
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        if(data.equals(assID)){
                            isconnected = true;
                            dos.writeUTF("Connected to PC: " + s.getLocalAddress());
                            Notifications.addToReel("Connected to Mobile client"); //add back when actually working
                        } else {
                            dos.writeUTF("Wrong connection");
                        }

                        dis.close();
                        dos.close();
                    }


                } catch (IOException e){
                    e.printStackTrace();

                }
            }

        };





        thread.start();


    }

}
