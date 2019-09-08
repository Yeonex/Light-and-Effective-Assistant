import Core.NameChanger;
import Core.Updater;
import Core.Version;
import UI.MainScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {
    private static boolean exists;
    private Preferences preferences = Preferences.userNodeForPackage(MainScene.class);


    public static void main(String[] args) throws Exception {
        Version version = new Version();
        Updater.getVersion(Version.getVersionString());
        try {
            Updater.checkForUpdate();
        } catch (IOException e){
            System.out.println("CloudFlair 403 protection! Update check failed");
        }
        exists = Preferences.userRoot().nodeExists("UI");
        //exists = false;
        if(!exists){
            System.out.println("x");
        }
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (exists) {
            new NameChanger(preferences.get("LEAName","LEA"));
            Parent root = FXMLLoader.load(getClass().getResource("UI/MainScene.fxml"));
            primaryStage.setTitle(Version.getVersionString());
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(new Scene(root,250,250));
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("/img/icon.png"));
            primaryStage.show();

        } else {

            Parent root = FXMLLoader.load(getClass().getResource("UI/Login.fxml"));
            primaryStage.setTitle(Version.getVersionString());
            primaryStage.setScene(new Scene(root, 590, 390));
            primaryStage.setResizable(false);
            primaryStage.show();

        }
    }




}
