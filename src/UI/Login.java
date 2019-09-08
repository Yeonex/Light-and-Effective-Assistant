package UI;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;


import java.awt.*;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class Login implements Initializable {

   @FXML
   private Button createLocalAccount;

   @FXML
   private AnchorPane localUserMenu;


   @FXML
   private AnchorPane localUserMenuScreen;


   @FXML
   private CheckBox agreedToTOS;

   @FXML
   private Hyperlink TOSLink;


   @FXML
   private AnchorPane root;

   @FXML
   private ImageView house;

   @FXML
   private Button exitApp;

   @FXML
   private Hyperlink TOSLink1;






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        agreedToTOS.setSelected(false);
        createLocalAccount.setVisible(false);
        root.getStylesheets().add("/CSS/login.css");


        createLocalAccount.setOnAction(event -> {
            System.out.println("Click :3");
            try {
                createLocalUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exitApp.setOnAction(event -> {
            System.exit(0);
        });

        agreedToTOS.setOnAction(event -> {
            if(agreedToTOS.isSelected()){
                createLocalAccount.setVisible(true);
                exitApp.setVisible(false);
            } else {
                createLocalAccount.setVisible(false);
                exitApp.setVisible(true);
            }
        });



        TOSLink.setOnAction(event -> launchLink());
        TOSLink1.setOnAction(event -> appLink());



    }


    private void createLocalUser() throws Exception{
        Preferences preferences = Preferences.userNodeForPackage(Login.class);
        InetAddress address = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        byte[] mac = ni.getHardwareAddress();
        StringBuilder sb = new StringBuilder(18);
        for(int i=0; i< mac.length;i++){
            sb.append(String.format("%02x",mac[i]));
        }
        String localID = sb.toString();
        System.out.println(localID);
        preferences.put("LEA_LOCAL_MCHN_ID",localID);
        System.exit(0);

    }

    private void launchLink(){
        try{
            Desktop.getDesktop().browse(new URL("http://ianzakharov.com/tos.html").toURI());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void appLink(){
        try{
            Desktop.getDesktop().browse(new URL("http://ianzakharov.com/mobile.php").toURI());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
