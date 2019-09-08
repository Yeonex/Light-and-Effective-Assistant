package Core;

public class Version {
    private static final String VERSION = "0.04";

    public static void Version(String ver) {
        System.out.println("Current Version of app : " + VERSION + "Version class");


    }

    public static double getVersionDouble(){
        return Double.parseDouble(VERSION);
    }

    public static String getVersionString(){
        return VERSION;
    }
}
