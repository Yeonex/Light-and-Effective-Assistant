package Core;

import UI.MainMenu;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WebHisotry implements Initializable {


    MainMenu mm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       System.out.println(System.getProperties().getProperty("os.name"));
       if(System.getProperties().getProperty("os.name").contains("Windows")){
           System.out.println("you have Windows");
           System.out.println(System.getProperty("user.home") + "\\App");
       }
    }
    public void setMainScene(MainMenu mainMenu) {
        this.mm = mainMenu;
    }

}
