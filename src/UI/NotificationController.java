package UI;

import Core.Notifications;
import Core.ToDoListEvents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    @FXML
    private ListView listView;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button dismissAll;

    @FXML
    private Button dismiss;

    @FXML
    private Label text;

    private MainScene mainStage;

    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Notifications.getNotificationList().size() == 0){
            listView.setVisible(false);
        } else {
            listView.setVisible(true);
           list.addAll(Notifications.getNotificationList());
           listView.setItems(list);
        }


        refreshBtn.setOnAction(event -> {
            refreshList();
        });

        dismiss.setOnAction(event -> {
            Notifications.getNotificationList().remove(listView.getFocusModel().getFocusedItem());
            refreshList();
        });

        dismissAll.setOnAction(event -> {
            Notifications.getNotificationList().clear();
            refreshList();
        });
    }

    public void setMainScene(MainScene mainScene){
        this.mainStage = mainScene;
    }

    public void refreshList(){
        if(Notifications.getNotificationList().size() == 0){
            listView.setVisible(false);
            listView.refresh();
            return;
        }
        listView.setVisible(true);
        list.removeAll();
        list.addAll(Notifications.getNotificationList());
        listView.refresh();
        listView.setItems(list);

    }
}
