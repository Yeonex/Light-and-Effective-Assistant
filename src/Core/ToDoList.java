package Core;

import UI.MainMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ToDoList implements Initializable {

    @FXML
    private Button addEventbtn;


    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField eventText;

    @FXML
    private ListView<ToDoListEvents> eventsListView;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button editBtn;


    ObservableList<ToDoListEvents> list = FXCollections.observableArrayList();

    private MainMenu mainMenu;
    private int overdue;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            FileInputStream fis = new FileInputStream(new File("tdl.obj"));
            ObjectInput objectInput = new ObjectInputStream(fis);
            ToDoListEvents tdle = (ToDoListEvents) objectInput.readObject();
            boolean cont = true;
            while (cont){
                if(tdle !=null){
                    list.add(tdle);
                    try {
                        tdle = (ToDoListEvents) objectInput.readObject();
                    } catch (EOFException e){
                        cont = false;
                    }
                } else{
                    cont = false;
                }
            }
            objectInput.close();
            fis.close();
        } catch (FileNotFoundException e ){
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("EOF?");
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("xxx");
        }

        if(list.size() > 0){
            eventsListView.setItems(list);
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getDate().toEpochDay() < LocalDate.now().toEpochDay()){
                overdue = overdue +1;
            }
        }if(overdue > 0){
            Notifications.addToReel("Over Due Task: " + overdue + "*");


        }

        confirmBtn.setVisible(false);



       datePicker.setValue(LocalDate.now());



        addEventbtn.setOnAction(event -> {
            System.out.println("the following event was added:");
            System.out.println(datePicker.getValue());
            System.out.println(eventText.getText());
            list.add(new ToDoListEvents(eventText.getText(),datePicker.getValue()));
            eventsListView.setItems(list);
            refresh();
            saveLocal(list);

        });


        editBtn.setOnAction(event -> {
            editBtn.setVisible(false);
            confirmBtn.setVisible(true);
            addEventbtn.setDisable(true);
            eventsListView.getFocusModel().getFocusedIndex();
            eventText.setText(eventsListView.getFocusModel().getFocusedItem().getDescription());
            datePicker.setValue(eventsListView.getFocusModel().getFocusedItem().getDate());
        });

        confirmBtn.setOnAction(event -> {
            list.get(eventsListView.getFocusModel().getFocusedIndex()).setDescription(eventText.getText());
            list.get(eventsListView.getFocusModel().getFocusedIndex()).setDate(datePicker.getValue());
            confirmBtn.setVisible(false);
            editBtn.setVisible(true);
            addEventbtn.setDisable(false);
            eventsListView.refresh();
            saveLocal(list);
        });

        deleteBtn.setOnAction(event -> {
                list.remove(eventsListView.getFocusModel().getFocusedItem());
                saveLocal(list);
        });

    }

    public void setMainScene(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void refresh(){
        datePicker.setValue(LocalDate.now());
        eventText.setText(null);

    }

    private void saveLocal(ObservableList<ToDoListEvents> list){
        try {
            FileOutputStream fos = new FileOutputStream(new File("tdl.obj"));
            ObjectOutput obj = new ObjectOutputStream(fos);
            for(int i= 0; i < list.size(); i++){
                obj.writeObject(list.get(i));
                }
            obj.close();
            fos.close();
        } catch (FileNotFoundException e){
            new AlertDialog().displayCritical("File not found","","0001","file tdl.obj not found");
        } catch(IOException e){
            new AlertDialog().displayCritical("object null","Objects where not found","0002","obj not found");
        }
    }

}
