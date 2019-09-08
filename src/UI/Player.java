package UI;

import Core.Notifications;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Player implements Initializable {

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnBack;

    private MainMenu mainMenu;

    private int track;

    @FXML
    private Label trackName;

    private Clip clip;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        track = 1;
        try {
            clip = AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnPlay.setOnAction(event -> {
            try {
                getTrack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnStop.setOnAction(event -> {
            clip.stop();
            clip.close();
            trackName.setText("Stopped!");
        });

        btnNext.setOnAction(event -> {
            track = track+1;
            if(track > 3){
                track =1;
            }
            try {
                getTrack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnBack.setOnAction(event -> {
            track = track -1;
            if(track < 1){
                track = 3;
            }
            try {
                getTrack();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }


    public void setMainScene(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }


    private void getTrack() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        switch (track){
            case 1:
               clip.open(AudioSystem.getAudioInputStream(Notifications .class.getClassLoader().getResource("music/56.wav")));
                trackName.setText("Track: 56");
               clip.start();
               break;
            case 2:
                clip.open(AudioSystem.getAudioInputStream(Notifications .class.getClassLoader().getResource("music/skidrow.wav")));
                trackName.setText("Track: Skidrow. Escaping fate.");
                clip.start();
                break;
            case 3:
                clip.open(AudioSystem.getAudioInputStream(Notifications .class.getClassLoader().getResource("music/Tomb.wav")));
                trackName.setText("Track: Tomb Raider 2 Main theme");
                clip.start();
                break;

        }
    }

//    Clip clip = AudioSystem.getClip();
//                clip.open(AudioSystem.getAudioInputStream(Notifications .class.getClassLoader().getResource("sound/not2.wav")));
//                clip.start();
}
