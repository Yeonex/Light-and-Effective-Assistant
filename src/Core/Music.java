package Core;

import UI.MainMenu;
import UI.MainScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLIFrameElement;


import java.net.URL;
import java.util.ResourceBundle;

public class Music implements Initializable {

    private MainMenu mm;

    @FXML
    private WebEngine webEngine;

    @FXML
    private WebView webView;

    @FXML
    private Button youtube;

    @FXML
    private Button spotify;

    @FXML
    private TextField url;

    @FXML
    private AnchorPane root;

    @FXML
    private CheckBox aot;

    @FXML
    private Button exit;

    private double posX,posY;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        youtube.setOnAction(event -> {
            String[] slice = url.getText().split("/");
            if(slice.length == 4 ){
                String[]pros = slice[3].split("watch\\?v=");
                webView.getEngine().load("https://www.youtube.com/embed/" + pros[1]);
                System.out.println(pros[1]);
                url.setText(null);
            } else {
                url.setText("Error ! invalid youtube url!");
            }

           // webView.getEngine().load("");
        });

        root.setOnMouseEntered(event -> {
            root.getScene().getWindow().setHeight(450);
            mm.getMusicStageRoot().getScene().setFill(Color.WHITE);
            exit.setVisible(true);
            aot.setVisible(true);
        });

        root.setOnMouseExited(event -> {
            if(!url.isFocused()){
                root.getScene().getWindow().setHeight(390);
                mm.getMusicStageRoot().getScene().setFill(Color.TRANSPARENT);
                exit.setVisible(false);
                aot.setVisible(false);

            }

        });

        root.focusedProperty().addListener(event ->{
            root.getScene().getWindow().setHeight(360);
        });



        root.setOnMouseDragged(event -> {
            root.getScene().getWindow().setX(event.getScreenX() - posX);
            root.getScene().getWindow().setY(event.getScreenY() - posY);
        });

        root.setOnMousePressed(event -> {
            posX = event.getX();
            posY = event.getY();
        });

        webView.setOnMousePressed(event -> {
            posX = event.getX();
            posY = event.getY();
        });

        aot.setOnAction(event -> {
            alwaysOnTop(aot.isSelected());
        });

        exit.setOnAction(event -> {
            webView.getEngine().load("");
            root.getScene().getWindow().hide();
        });










    }

    public void setMainScene(MainMenu mainMenu) {
        this.mm = mainMenu;
    }

    public void alwaysOnTop(boolean set){
        ((Stage) root.getScene().getWindow()).setAlwaysOnTop(set);

    }
}


