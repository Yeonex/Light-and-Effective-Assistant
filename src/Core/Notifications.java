package Core;



import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Notifications {

    private static ArrayList<String> notificationReel = new ArrayList<>();
    // abs : D:\Users\ianza\Documents\lea\src\sound\not2.wav

    public static File notificationSound = new File("/sound/not2.wav");

    public static boolean playSound = true;

    public static void addToReel(String notification){
        try{
            System.out.println("Notification : "  + notification + " ||was added to reel");
            if(playSound){
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(Notifications.class.getClassLoader().getResource("sound/not2.wav")));
                clip.start();
            }
        } catch (LineUnavailableException e){
            new AlertDialog().displayCritical("File not found","","0001","Line was not unavailable");
        } catch (IOException e){
             new AlertDialog().displayCritical("File not found","","0002","file "+ notificationSound.getPath() +" not found");
        } catch (UnsupportedAudioFileException e) {
            new AlertDialog().displayCritical("File not found","","0003"," was not supported!");
            e.printStackTrace();
        }
        notificationReel.add(notification);
    }

   public static ArrayList<String> getNotificationList(){
        return notificationReel;
   }



   private static Notifications instance;


}
