package UI;

import Core.*;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import voice.commands.CommandProcessor;


import javax.management.Notification;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class MainScene implements Initializable {
    private String VERSION;

    @FXML
    private Button updateButton;

    @FXML
    private ImageView avatar;

    @FXML
    private AnchorPane root;

    @FXML
    private Button exit;

    @FXML
    private Button menu;

    @FXML
    private Button sync;

    @FXML
    private Button settings;

    @FXML
    private ImageView clock;

    @FXML
    private Label clockTopLable;

    @FXML
    private Label clockBottomLable;

    @FXML
    private Label dorn;

    @FXML
    private Button notification;

    @FXML
    private Button warning;

    @FXML
    private Label batteryStatus;

    @FXML
    private Button voice;

    @FXML
    private Label syncLable;

    @FXML
    private Pane bindToFixture;





    private double posX,posY;
    private Stage settingsStage;
    private Stage notificationStage;
    private Stage menuStage;
    private Stage issueStage;
    private Timeline clockLogic;
    private MainMenu mainMenu;
    private LiveSpeechRecognizer recognizer;
    private CommandProcessor cmdpros = new CommandProcessor();

    public String assistName;
    private MobileConnectionHandler MCH;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences preferences = Preferences.userNodeForPackage(MainScene.class);
       // System.err.close();
        notification.setStyle("-fx-stroke: RED; -fx-stroke-width: 5px; ");
        //temp :        <tmp>25</tmp>  icon#        <icon>34</icon>
        //Weather weather = new Weather(); // Works just add it to scence



        assistName = preferences.get("LEAName","LEA");
        Notifications.playSound = preferences.getBoolean("notificationSound",true);
        initSettings();
        initMainMenu();
        initNotifcation();
        Issue.Issue();
        initIssueStage();
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setGrammarPath("resource:/res/");
        System.out.println( configuration.getGrammarPath());
        configuration.setGrammarName("grammar");
        System.out.println(configuration.getGrammarName());
        configuration.setUseGrammar(true);
        syncLable.setText("");
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("wow it did work");
            voice.setDisable(true);
            Issue.addIssue("Voice doesnt work");
        }



        String uriAva = preferences.get("avatarImagePath","/img/def.gif");
        avatar.setImage(new Image(uriAva,250,250,false,false));
        if(uriAva.contains("def") || uriAva.contains("fest") || uriAva.contains("boss")){
            avatar.setY(avatar.getY() - 30);
            avatar.setX(avatar.getX() + 10);
           avatar.setScaleY(1.6);
           avatar.setScaleX(1.6);
           avatar.setPreserveRatio(true);
        }
        root.getStylesheets().add("/CSS/Core.css");
        if(preferences.getBoolean("hideBg",false)){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    root.getScene().setFill(Color.TRANSPARENT);
                }
            });
        } else {
          Color colorValue = Color.valueOf(preferences.get("BackgroundColorPref","0xffffffff"));
          if(colorValue != null){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setBackgroundColor(colorValue);
                }
            });
          }
        }

        double hueValue = Double.parseDouble(preferences.get("huePref","0"));
        if(hueValue !=0.0){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ColorAdjust ca = new ColorAdjust();
                    ca.setHue(hueValue);
                    clock.setEffect(ca);
                }
            });
        }

        if(preferences.getBoolean("alwaysOnTop",false)){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ((Stage) root.getScene().getWindow()).setAlwaysOnTop(preferences.getBoolean("alwaysOnTop",false));
                }
            });

            if(!preferences.getBoolean("showClock", true)){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        toggleClock(true);
                    }
                });
            }
        }
        if(Issue.numberOfIssues() > 0){
            warning.setText("P");
            warning.setTextFill(Color.YELLOW);
        } else {
            warning.setText("");
        }
//        if(Notifications.getNotificationList().size() > 0){
//            notification.setText(String.valueOf(Notifications.getNotificationList().size()) + "*");
//
//        }
        Thread notificationThread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int lastKnowNotificationSize = Notifications.getNotificationList().size();
                            notification.setText("\uD83D\uDD14 " + String.valueOf(lastKnowNotificationSize) + "*");
                            notification.setTextFill(Color.INDIANRED);
                            if (lastKnowNotificationSize <= 0) {
                                notification.setText("\uD83D\uDD14 ");
                            }
                            if (lastKnowNotificationSize != Notifications.getNotificationList().size()) {
                                lastKnowNotificationSize = Notifications.getNotificationList().size();
                                notification.setText(String.valueOf(lastKnowNotificationSize) + "*");
                                notification.setStyle("-fx-stroke: RED; -fx-stroke-width: 5px; ");
                            }

                            if(Issue.numberOfIssues() <= 0){
                                warning.setText("");
                            } else if (Issue.numberOfIssues() >0){
                                warning.setText("⚠");
                                warning.setScaleX(2);
                                warning.setScaleY(2);
                                warning.setTextFill(Color.YELLOW);
                                warning.setStyle("-fx-stroke: firebrick; -fx-stroke-width: 100px; ");
                                warning.getStylesheets().add("/CSS/Core.css");
                            }

                            if(PhoneBatteryComponent.phoneIsConnected){
                                batteryStatus.setVisible(true);
                                int localbp = PhoneBatteryComponent.intBP;
                                batteryStatus.setText("\uD83D\uDD0B " + localbp+"%");

                                if(localbp >= 60){
                                    batteryStatus.setTextFill(Color.GREEN);
                                } else if (localbp >20){
                                    batteryStatus.setTextFill(Color.YELLOW);
                                } else {
                                    batteryStatus.setTextFill(Color.RED);
                                    Notifications.addToReel("Warning Low phone battery! " + localbp + "%");
                                }

                            }else {
                                batteryStatus.setVisible(false);
                            }


                        }
                    });
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }; notificationThread.start();


        notification.setOnAction(event -> {
            notificationStage.show();
        });

        warning.setOnAction(event -> {
            issueStage.show();
        });

        if(preferences.get("showClock","1").equals("0")){
            clock.setVisible(false);
            clockTopLable.setVisible(false);
            clockBottomLable.setVisible(false);
            dorn.setVisible(false);
        }


        if(preferences.get("showAvatar","1").equals("0")){
            avatar.setVisible(false);
        }

        if(Updater.getUpdateStatus()){
            updateButton.setVisible(true);
        } else{
            updateButton.setVisible(false);
        }


        updateButton.setOnAction(event -> {
            System.out.println("Updating");
            System.out.println("isnt working");

            try {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI oURL = new URI("http://ianzakharov.com/download.php");
                desktop.browse(oURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exit.setOnAction(event -> {
            System.exit(0);
        });
        sync.setText("\uD83D\uDD04 Sync");
        sync.setOnAction(event -> {
            System.out.println("will Sync");
            syncLable.setText("⇄");
            syncLable.setScaleY(2);
            syncLable.setScaleX(2);
            syncLable.setTextFill(Color.AQUA);
            networkISActive();
          //  MobileSyncComponent.StartSyncComponet();
            try {
                //original port 27015
              MCH  = new MobileConnectionHandler(28000, this);
              MCH.initSever();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        root.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        root.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            System.out.println("was dad");
            if(db.hasString()){
                System.out.println("String");
            }
            if(db.hasFiles()){
                System.out.println("files");
                MCH.getConnectionHandler().sendFile(event.getDragboard().getFiles().get(0));
            }
            event.setDropCompleted(true);
            event.consume();
        });

        voice.setOnMousePressed(event -> {
            System.out.println("voice started!");
            recognizer.startRecognition(true);
            new Thread(()-> {
                while (true) {

                    String words = recognizer.getResult().getHypothesis();
                    if (words.equals("hey lea stop")) {
                       break;

                    }
                    else {
                        System.out.println(words);
                        words = words.replace("hey lea " , "");
                        cmdpros.runCommand(words);
                        if(words.contains("settings")){
                            if(words.contains("open")) Platform.runLater(() ->{settingsStage.show();});
                            else if (words.contains("close"))Platform.runLater(()->{settingsStage.hide();});

                        }
                        if(words.contains("menu")){
                            if(words.contains("show") || words.contains("open")) Platform.runLater(() ->{menuStage.show();});
                            else if (words.contains("hide") || words.contains("close")) Platform.runLater(()->{menuStage.hide();});
                        }
                        if(words.contains("kill")){
                            System.exit(0);
                        }

                        if(words.contains("to do list")){
                            if(words.contains("show") || words.contains("open"))Platform.runLater(()->{mainMenu.getToDoListStage().show();});
                            else if(words.contains("close") || words.contains("hide"))Platform.runLater(()-> mainMenu.getToDoListStage().hide());
                        }

                        if(words.contains("music")){
                            if(words.contains("show") || words.contains("open"))Platform.runLater(()->{mainMenu.getMusicStage().show();});
                            else if (words.contains("close") || words.contains("hide"))Platform.runLater(()->{mainMenu.getMusicStage().hide();});
                        }

                        if(words.contains("notes")){
                            if(words.contains("show") || words.contains("open"))Platform.runLater(()->{mainMenu.getNoteStage().show();});
                            else if (words.contains("close") || words.contains("hide"))Platform.runLater(()->{mainMenu.getNoteStage().hide();});
                        }

                        if(words.contains("weather")){
                            if(words.contains("show") || words.contains("open"))Platform.runLater(()->{mainMenu.getWeatherStage().show();});
                            else if (words.contains("close") || words.contains("hide"))Platform.runLater(()->{mainMenu.getWeatherStage().hide();});
                        }
                    }
                }
                recognizer.stopRecognition();
                System.out.println(this + " has stopped!");
            }).start();


        });

        voice.setOnMouseReleased(event -> {
            System.out.println("voice stopped!");

        });

        menu.setOnAction(event -> {
            System.out.println("opens menu");
                menuStage.show();
        });

        settings.setOnAction(event -> {
            System.out.println("opens settings");
            settingsStage.show();
        });

        clockLogic = new Timeline(new KeyFrame(Duration.ZERO,event -> {
            SimpleDateFormat sdf = new SimpleDateFormat("E dd MMM yyyy");
            Calendar calendar = Calendar.getInstance();
            clockTopLable.setText(sdf.format(calendar.getTime()));
            int minute = LocalDateTime.now().getMinute();
            int hour = LocalDateTime.now().getHour();
            if(hour > 19 || hour < 5){
                dorn.setText("\uD83C\uDF19");
            } else {
                dorn.setText("☀");
            }
            if(hour < 10 && minute < 10){
                clockBottomLable.setText("0" + hour + " : 0" + minute);
            }else if(hour < 10){
                clockBottomLable.setText("0" +hour + " : " + minute);
            } else if(minute < 10){
                clockBottomLable.setText(hour + " : 0" + minute);
            } else{
                clockBottomLable.setText(hour + " : " + minute);
            }
        }), new KeyFrame(Duration.seconds(60)));
        clockLogic.setCycleCount(Animation.INDEFINITE);
        clockLogic.play();

        root.setOnMouseDragged(event -> {
            root.getScene().getWindow().setX(event.getScreenX() - posX);
            root.getScene().getWindow().setY(event.getScreenY() - posY);
        });

        root.setOnMousePressed(event -> {
            posX = event.getX();
            posY = event.getY();
        });

    }

    public void pendingUpdate(boolean outOfDate){
        if(outOfDate){
            updateButton.setVisible(true);
        }
    }

    public void setAlwaysOnTop(boolean set){
        System.out.println(set);
        ((Stage) root.getScene().getWindow()).setAlwaysOnTop(set);
    }

    public void toggleAvatart(boolean toggle){
        avatar.setVisible(!toggle);
    }

    public void toggleClock(boolean toggle){
        clockTopLable.setVisible(!toggle);
        clockBottomLable.setVisible(!toggle);
        clock.setVisible(!toggle);
        dorn.setVisible(!toggle);
        if(! toggle){
            clockLogic.stop();
        } else {
            clockLogic.play();
        }
    }

    public void toggleBG(boolean toggle){
        if(!toggle) {
            root.getScene().setFill(Color.TRANSPARENT);
        } else {
            root.getScene().setFill(Color.WHITE);
        }
    }

    public void setAvatar(InputStream loc){
        avatar.setScaleX(1);
        avatar.setScaleY(1);
        Image image = new Image(loc);
        avatar.setImage(image);

    }



    public void setBackgroundColor(Color color){
        root.getScene().setFill(color);

    }

    public void setHueColor(ColorAdjust ca){
        clock.setEffect(ca);
    }



    public String getVERSION(){
        return VERSION;
    }

    private void initSettings(){
        if(settingsStage == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
                Parent root1 = fxmlLoader.load();
                Settings s = fxmlLoader.getController();
                s.setMainScene(MainScene.this);
                settingsStage = new Stage();
                settingsStage.setTitle(VERSION);
                settingsStage.setScene(new Scene(root1));
                settingsStage.getIcons().add(new Image("/img/icon.png"));
                settingsStage.setResizable(false);
                settingsStage.setTitle(assistName + " - Settings");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void initNotifcation(){
        if(notificationStage == null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notificationsView.fxml"));
                Parent root1 = fxmlLoader.load();
                NotificationController nc = fxmlLoader.getController();
               nc.setMainScene(MainScene.this);
                notificationStage = new Stage();
                notificationStage.setTitle(VERSION);
                notificationStage.setScene(new Scene(root1));
                notificationStage.getIcons().add(new Image("/img/icon.png"));
                notificationStage.setResizable(false);
                notificationStage.setTitle(assistName + " - Notifications");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void initIssueStage(){
        if(issueStage == null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("issueView.fxml"));
                Parent root1 = fxmlLoader.load();
                IssueController iv = fxmlLoader.getController();
                iv.setMainScene(MainScene.this);
                issueStage = new Stage();
                issueStage.setTitle(VERSION);
                issueStage.setScene(new Scene(root1));
                issueStage.getIcons().add(new Image("/img/icon.png"));
                issueStage.setResizable(false);
                issueStage.setTitle(assistName + " - Warnings");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void initMainMenu() {
        if (menuStage == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                Parent root1 = fxmlLoader.load();
                MainMenu mm = fxmlLoader.getController();
                mm.setMainScene(MainScene.this);
                menuStage = new Stage();
                menuStage.setTitle(VERSION);
                menuStage.setScene(new Scene(root1));
                menuStage.getIcons().add(new Image("/img/icon.png"));
                menuStage.setTitle(assistName + " - Menu v:" + Version.getVersionString());
                menuStage.setResizable(false);
                mainMenu = mm;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }

    public  void networkISActive(){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), syncLable);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(5);
        fadeTransition.play();
    }

    public void swapAvaClock(boolean selected) {
        if(selected){
            clock.setY(110);
            avatar.setY(-100);
            dorn.setLayoutY(170);
            clockBottomLable.setLayoutY(170);
            clockTopLable.setLayoutY(149);
            Issue.addIssue("Test issue");
        } else {
            clock.setX(0);
            clock.setY(0);
            avatar.setX(0);
            avatar.setY(0);
            dorn.setLayoutY(61);
            clockBottomLable.setLayoutY(61);
            clockTopLable.setLayoutY(42);
        }
    }
}
