package Core;



import java.io.Serializable;
import java.time.LocalDate;

public class NoteListManager implements Serializable {

    private String title;
    private String description;
    private LocalDate date;
    private String iD;

    public  NoteListManager(String noteTile,String desc){
        this.title = noteTile;
        this.description = desc;
        this.date = LocalDate.now();
        this.iD = null;

    }

    public  String getDescription(){return  description;}

    public  LocalDate getDate() {return date;}

    public String getId(){return iD;}

    public String getTitle(){return title;}

    public void setDescription(String content){ this.description = content;}

    public void setDate(LocalDate d){this.date = d;}

    public void setiD(String id){this.iD = id;}

    public void setTitle(String title){this.title = title;}

    @Override
    public String toString(){
        return "Date: " + getDate() + " || Title: " + getTitle();
    }
}

