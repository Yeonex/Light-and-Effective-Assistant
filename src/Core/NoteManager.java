package Core;

import UI.MainMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;


import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NoteManager implements Initializable {

    @FXML
    Button toggleList;

    @FXML
    Button save;

    @FXML
    Button newNote;

    @FXML
     TextArea notesContent;

    @FXML
    ListView<NoteListManager> ListView;

    @FXML
    TextField noteTitle;

    ObservableList<NoteListManager> list = FXCollections.observableArrayList();


    private MainMenu mainMenu;
    private String currentContent ="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Content isn't saved!");
        alert.setContentText("Would you like to save?");


        try{
            FileInputStream fis = new FileInputStream(new File("Note.txt"));
            ObjectInput objectInput = new ObjectInputStream(fis);
            NoteListManager nle = (NoteListManager) objectInput.readObject();
            boolean cont = true;
            while (cont){
                if(nle !=null){
                    list.add(nle);
                    try {
                        nle = (NoteListManager) objectInput.readObject();
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
            ListView.setItems(list);
        }


        toggleList.setOnAction(event -> {
            if(notesContent.isVisible()){
                notesContent.setVisible(false);
            } else {
                notesContent.setVisible(true);
            }
        });


       newNote.setOnAction(event -> {
          if(!notesContent.getText().equals("")){
              if(!currentContent.equals(notesContent.getText())){
                  Optional<ButtonType> result = alert.showAndWait();
                  if(result.get() == ButtonType.OK){
                      System.out.println("will save");
                      currentContent = notesContent.getText();
                      checkIfTileisEmpty();
                      list.add(new NoteListManager(noteTitle.getText(),notesContent.getText()));
                      saveLocal(list);
                  }
                  else {
                      System.out.println(" procceded to clear");
                      notesContent.setText("");
                      noteTitle.setText(noteTitle.getPromptText());
                  }
              }
              noteTitle.setText(noteTitle.getPromptText());
              notesContent.setText("");
          }

       });

       save.setOnAction(event -> {
           if(!notesContent.getText().equals("")){
               checkIfTileisEmpty();
               if(noteTitle.getText().equals("")){
                   list.add(new NoteListManager(noteTitle.getPromptText(),notesContent.getText()));
               } else {
                   list.add(new NoteListManager(noteTitle.getText(),notesContent.getText()));
               }
               System.out.println("should have saved");
               saveLocal(list);
           }
       });

       ListView.setOnMouseClicked(event -> {
           if(event.getButton().equals(MouseButton.PRIMARY)){
               if(event.getClickCount() == 2){
                   System.out.println("click counted : " + event.getClickCount());
                   if(!notesContent.getText().equals("")){
                       Optional<ButtonType> result = alert.showAndWait();
                       if(result.get() == ButtonType.OK){
                           System.out.println("will save and store current text!");
                           checkIfTileisEmpty();
                           list.add(new NoteListManager(noteTitle.getText(),notesContent.getText()));
                           saveLocal(list);
                       }
                   }
                   notesContent.setVisible(true);
                   notesContent.setText(ListView.getFocusModel().getFocusedItem().getDescription());
                   noteTitle.setText(ListView.getFocusModel().getFocusedItem().getTitle());
                   currentContent = notesContent.getText();
               }

           }
       });

    }

    private void saveLocal(ObservableList<NoteListManager> list){
        try {
            FileOutputStream fos = new FileOutputStream(new File("Note.txt"));
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





    public void setMainScene(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    private void checkIfTileisEmpty(){
        if(noteTitle.getText().equals("")){
            noteTitle.setText(noteTitle.getPromptText());
        }
    }
}
