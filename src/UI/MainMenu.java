package UI;

import Core.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private Button toDoListBtn;

    @FXML
    private Button notesBtn;

    @FXML
    private Button webHistoryBtn;

    @FXML
    private Button musicBtn;

    @FXML
    private Button mailBtn;

    @FXML
    private Button weatherBtn;

    @FXML
    private Button pathBtn;

    @FXML
    private Button stats;

    @FXML
    private Button msgBtn;



    private MainScene mainScene;

    private Stage toDoListStage;
    private Stage musicStage;
    private Parent musicStageRoot;
    private Stage webHistoryStage;
    private Stage weatherStage;
    private Stage noteStage;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initToDoList();
        initMusic();
        initNotes();
        initWeather();

        toDoListBtn.setOnAction(event -> {
            System.out.println("Opens the to do list app!");
            System.out.println("soonTM");
            toDoListStage.show();
        });

        notesBtn.setOnAction(event -> {
            System.out.println("Opens notes");
            noteStage.show();

        });
        webHistoryBtn.setOnAction(event -> {
            if(webHistoryStage == null){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("player.fxml"));
                    Parent root1 = fxmlLoader.load();
                    Player webHisotry= fxmlLoader.getController();
                    webHisotry.setMainScene(MainMenu.this);
                    webHistoryStage = new Stage();
                    webHistoryStage.setTitle(mainScene.getVERSION());
                    webHistoryStage.setScene(new Scene(root1));
                    weatherStage.setResizable(false);
                    webHistoryStage.show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                webHistoryStage.show();
            }
        });
        musicBtn.setOnAction(event -> {
                musicStage.show();
        });
        mailBtn.setDisable(true);
        weatherBtn.setOnAction(event -> {
            weatherStage.show();
        });
        pathBtn.setOnAction(event -> {
            //http://ianzakharov.com/patch.html
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = null;
            try {
                oURL = new URI("http://ianzakharov.com/download.php");
                desktop.browse(oURL);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        stats.setDisable(true);
        msgBtn.setDisable(true);


    }

    public void setMainScene(MainScene mainScene) {
        this.mainScene = mainScene;
    }

    public Stage getMusicStage(){
        return musicStage;
    }

    public Parent getMusicStageRoot(){
        return musicStageRoot;
    }

    public Stage getWeatherStage(){return  weatherStage;}


    public MainScene getMain(){
        return  mainScene;
    }

    public Stage getToDoListStage(){
        return toDoListStage;
    }

    public Stage getNoteStage(){return  noteStage;}

    private void initToDoList(){
        if(toDoListStage == null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("todolist.fxml"));
                Parent root1 = fxmlLoader.load();
                ToDoList toDoList = fxmlLoader.getController();
                toDoList.setMainScene(MainMenu.this);
                toDoListStage = new Stage();
                toDoListStage.setTitle(Version.getVersionString());
                toDoListStage.setScene(new Scene(root1));
            } catch (Exception e){
                e.printStackTrace();
            }
    }}

    private void  initMusic() {
        if (musicStage == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("music.fxml"));
                Parent root1 = fxmlLoader.load();
                Music music = fxmlLoader.getController();
                music.setMainScene(MainMenu.this);
                musicStage = new Stage();
                musicStage.setTitle(Version.getVersionString());
                musicStage.setScene(new Scene(root1, 600, 450));
                musicStage.setResizable(false);
                musicStage.initStyle(StageStyle.TRANSPARENT);
                musicStage.isAlwaysOnTop();
                root1.getScene().setFill(Color.TRANSPARENT);
                musicStageRoot = root1;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initWeather(){
        if (weatherStage == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("weather.fxml"));
                Parent root1 = fxmlLoader.load();
                WeatherControl weatherControl = fxmlLoader.getController();
                weatherControl.setMainScene(MainMenu.this);
                weatherStage = new Stage();
                weatherStage.setTitle(Version.getVersionString());
                weatherStage.setScene(new Scene(root1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initNotes(){
        if(noteStage == null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notes.fxml"));
                Parent root1 = fxmlLoader.load();
                NoteManager noteManager = fxmlLoader.getController();
                noteManager.setMainScene(MainMenu.this);
                noteStage = new Stage();
                noteStage.setTitle(Version.getVersionString());
                noteStage.setScene(new Scene(root1));
            }  catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
