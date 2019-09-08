package UI;

import Core.Notifications;
import Core.Weather;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherControl implements Initializable {
    @FXML
    private Label tempLable;

    @FXML
    private ImageView imageIcon;

    private boolean weatherPassed;
    private MainMenu mm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherPassed =false;
        try {
            Weather weather = new Weather(); // Works just add it to scence
            weatherPassed = weather.gotWeather();
            if(weatherPassed){
                tempLable.setText(weather.getTemp()+"ºC");
                System.out.println("/img/weather/"+weather.getIcon()+".png");
               // InputStream loc = new FileInputStream("/img/weather/"+weather.getIcon()+".png");
                String path = "/img/weather/"+weather.getIcon()+".png";
                String test = "/img/weather/34.png";
                imageIcon.setImage(new Image(this.getClass().getResourceAsStream("/img/weather/"+weather.getIcon()+".png")));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMainScene(MainMenu mainMenu){
        mm = mainMenu;
    }
}
