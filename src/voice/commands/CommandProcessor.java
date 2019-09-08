package voice.commands;

import Core.VoiceCommand;

import java.util.HashMap;

public class CommandProcessor {

    private  HashMap<String, VoiceCommand> commandHashMap = new HashMap<>();

    public CommandProcessor(){
       commandHashMap.put("joke",new Joke());
       commandHashMap.put("browser", new Browser());
       commandHashMap.put("terminal", new Terminal());
    }


    public boolean runCommand(String c){
        System.out.println("command got was :" + c);
        if(c.contains("joke")){
            c = "joke";
            if(commandHashMap.containsKey(c)){
                commandHashMap.get(c).execute();
            }
        }
        if(c.contains("close")){
            c = c.replace("close ","");
            System.out.println(c);
            if(commandHashMap.containsKey(c)){
                commandHashMap.get(c).close();
            }
        } else if(c.contains("open")) {
            c = c.replace("open ","");
            System.out.println(c);
            if (commandHashMap.containsKey(c)) {
                commandHashMap.get(c).execute();
                return true;
            }
            return false;
        }
        return false;
    }

}
