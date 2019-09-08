package Core;

import java.io.Serializable;
import java.time.LocalDate;


public class ToDoListEvents implements Serializable {

    private String description;
    private LocalDate date;


    public ToDoListEvents(String desc, LocalDate d){
        this.description = desc;
        this.date = d;

    }

    public String getDescription(){
        return description;
    }

    public LocalDate getDate(){
        return date;
    }


    public void setDescription(String desc){
        this.description = desc;
    }

    public void setDate(LocalDate d){
        this.date = d;
    }


    @Override
    public String toString(){
        return "Date: " + getDate() + " || Description: " + getDescription();
    }
}
