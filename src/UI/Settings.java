package UI;


import Core.AlertDialog;
import Core.Issue;
import Core.Notifications;
import Core.SSID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;



import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Settings implements Initializable {

    @FXML
    private Label localID;

    @FXML
    private CheckBox alwaysOnTop;

    @FXML
    private CheckBox hideAvatar;

    @FXML
    private CheckBox DisableNotifcations;

    @FXML
    private CheckBox swapAvaClock;

    @FXML
    private CheckBox disbaleClock;

    @FXML
    private CheckBox hideBackground;

    @FXML
    private RadioButton avaDef;

    @FXML
    private RadioButton avaNier;

    @FXML
    private RadioButton avaFestive;

    @FXML
    private RadioButton avaCustom;

    @FXML
    private Label pathLable;

    @FXML
    private TextField pathURL;

    @FXML
    private Button findBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider slider;

    @FXML
    private Button confirmHue;

    @FXML
    private ImageView clockprev;

    @FXML
    private Button nameChnager;

    @FXML
    private TextField nameText;

    @FXML
    private AnchorPane settingsOne;

    @FXML
    private AnchorPane accountBtn;

    @FXML
    private AnchorPane settingsButton;

    @FXML
    private Label leaIPadress;

    private MainScene mainScene;

    private String path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences preferences = Preferences.userRoot().node("/UI");
        localID.setText("Security ID: " + preferences.get("LEA_LOCAL_MCHN_ID",""));
        SSID.setSsid(preferences.get("LEA_LOCAL_MCHN_ID",""));
        nameText.setText(preferences.get("LEAName","LEA"));
        final ToggleGroup avaSelection = new ToggleGroup();
        avaDef.setToggleGroup(avaSelection);
        String avatarPathSelectec = preferences.get("avatarImagePath","/img/def.gif").toString();
        if(avatarPathSelectec.equals("/img/def.gif")){
            avaDef.setSelected(true);
        } else if (avatarPathSelectec.equals("/img/boss_shadowlord_breathing.gif")){
            avaNier.setSelected(true);
        } else if (avatarPathSelectec.equals("/img/fest.gif")){
            avaFestive.setSelected(true);
        } else {
            avaCustom.setSelected(true);
        }

        settingsButton.getStylesheets().add("/CSS/Settings.css");
        accountBtn.getStylesheets().add("/CSS/Settings.css");

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println(inetAddress.getHostAddress().toString());
            leaIPadress.setText("IP address: " + inetAddress.getHostAddress().toString());
        } catch (Exception e){
            e.printStackTrace();
            leaIPadress.setText("IP address: Failed to grab IP from machine");
            Issue.addIssue("Failed to grab IP of machine");
        }

        if(preferences.getBoolean("hideBg",false)){
            hideBackground.setSelected(true);
        }
        if(!preferences.getBoolean("showClock",true)){
            disbaleClock.setSelected(true);
        }

        if(!preferences.getBoolean("notificationSound",true)){
            DisableNotifcations.setSelected(true);
        }

        avaNier.setToggleGroup(avaSelection);
        avaFestive.setToggleGroup(avaSelection);
        avaCustom.setToggleGroup(avaSelection);

        DisableNotifcations.setOnAction(event -> {
            if(DisableNotifcations.isSelected()){
                Notifications.playSound = false;
            } else {
                Notifications.playSound = true;
            }
            preferences.putBoolean("notificationSound",Notifications.playSound);
        });
      //  swapAvaClock.setDisable(true);
        swapAvaClock.setOnAction(event -> {
            mainScene.swapAvaClock(swapAvaClock.isSelected());
        });

        avaDef.setOnAction(event -> {
          //  mainScene.setAvatar("src/img/def.gif");
            mainScene.setAvatar(this.getClass().getResourceAsStream("/img/def.gif"));
            preferences.put("avatarImagePath","/img/def.gif");
        });

        avaNier.setOnAction(event -> {
          //  mainScene.setAvatar("src/img/boss_shadowlord_breathing.gif");
            mainScene.setAvatar(this.getClass().getResourceAsStream("/img/boss_shadowlord_breathing.gif"));
            preferences.put("avatarImagePath","/img/boss_shadowlord_breathing.gif");
        });

        avaFestive.setOnAction(event -> {
           // mainScene.setAvatar("src/img/fest.gif");
            mainScene.setAvatar(this.getClass().getResourceAsStream("/img/fest.gif"));
            preferences.put("avatarImagePath","/img/fest.gif");
        });

        pathLable.setVisible(avaCustom.isSelected());
        pathURL.setVisible(avaCustom.isSelected());
        findBtn.setVisible(avaCustom.isSelected());
        confirmBtn.setVisible(avaCustom.isSelected());

        avaCustom.setOnAction(event -> {
            pathLable.setVisible(avaCustom.isSelected());
            pathURL.setVisible(avaCustom.isSelected());
            findBtn.setVisible(avaCustom.isSelected());
            confirmBtn.setVisible(avaCustom.isSelected());
        });

        //avaCustom.setDisable(true);

        colorPicker.setValue(Color.CORAL);
        colorPicker.setOnAction(event -> {
            System.out.println(colorPicker.getValue());
            preferences.put("BackgroundColorPref",colorPicker.getValue().toString());
            mainScene.setBackgroundColor(colorPicker.getValue());
        });

        if(preferences.get("showAvatar","1").equals("0")){
            hideAvatar.setSelected(true);
        }
        // BUG?
        if (preferences.getBoolean("alwaysOnTop",false)){
            alwaysOnTop.setSelected(true);
        }

        alwaysOnTop.setOnAction(event -> {
           mainScene.setAlwaysOnTop(alwaysOnTop.isSelected());
           preferences.putBoolean("alwaysOnTop", alwaysOnTop.isSelected());

        });

        slider.valueProperty().addListener(event ->{
            ColorAdjust ca = new ColorAdjust();
            ca.setHue(slider.getValue());
            clockprev.setEffect(ca);
        });


        confirmHue.setOnAction(event -> {
            ColorAdjust ca = new ColorAdjust();
            ca.setHue(slider.getValue());
            clockprev.setEffect(ca);
            System.out.println(slider.getValue());
            mainScene.setHueColor(ca);
            preferences.put("huePref", String.valueOf(slider.getValue()));
        });


        hideAvatar.setOnAction(event -> {
            mainScene.toggleAvatart(hideAvatar.isSelected());
            if(hideAvatar.isSelected()){
                preferences.put("showAvatar","0");
            }else{
                preferences.put("showAvatar","1");
            }


        });

        disbaleClock.setOnAction(event -> {
            mainScene.toggleClock(disbaleClock.isSelected());
            if(disbaleClock.isSelected()){
                preferences.putBoolean("showClock", false);
            } else {
                preferences.putBoolean("showClock", true);
            }
        });

        hideBackground.setOnAction(event -> {
            mainScene.toggleBG(! hideBackground.isSelected());
            if(hideBackground.isSelected()){
                preferences.putBoolean("hideBg",true);
            } else {
                preferences.putBoolean("hideBg", false);
            }
        });

        nameChnager.setOnAction(event -> {
            preferences.put("LEAName",nameText.getText());
            new AlertDialog().displayInformation("Information","Name Changed will take effect on restart","00001",null);
        });

        accountBtn.setOnMouseClicked(event -> {
            settingsOne.setVisible(false);
        });


        settingsButton.setOnMouseClicked(event -> {
            settingsOne.setVisible(true);
        });

        findBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null){
                pathURL.setText(selectedFile.getPath());
                System.out.println(selectedFile.getPath().toString());
                InputStream convertion = null;
                try {
                    convertion = new FileInputStream(selectedFile);
                    mainScene.setAvatar(convertion);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setMainScene(MainScene mainScene) {
        this.mainScene = mainScene;
    }
}
