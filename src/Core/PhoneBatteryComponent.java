package Core;

public class PhoneBatteryComponent {
    public static boolean phoneIsConnected = false;
    public static String batteryPresent ="";
    public static int intBP = 100;

    public static void calculateBP(String msg){
        String rawValue = msg.replace("Battery: ","");
        System.out.println(rawValue);
        double num = Double.parseDouble(rawValue);
        intBP =(int)(num *100);
    }
}
