package Core;

import javafx.scene.control.Alert;

public class AlertDialog {
    //Alret types
    //Information         0x00
    //Without Header text 1x00
    //Warning             2x00
    //error               3x00
    //Critical            4x00


    public void displayCritical(String Title,String Header,String errorNum, String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header + " 4x"+errorNum);
        alert.setContentText(Content);

        alert.showAndWait();
    }

    public void displayInformation(String title, String header, String errorNum, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header + " 0x"+errorNum);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
