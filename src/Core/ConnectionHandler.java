package Core;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import sun.misc.IOUtils;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;


public class ConnectionHandler {

 private  WebSocket phoneClient;
 private State state;
 private DataType dataType;
 private File file2Send;
 private Queue<File> transferQueue;
 private String fileName;

 public ConnectionHandler( WebSocket client){
     phoneClient = client;
     state = State.HandShake;

 }

 private enum State{
     HandShake,
     Idle,
     Sending,
     Receiving,
     Conformation
 }

 private enum DataType{
     File,
     Notes,
     TodoList
 }

 public void updateState(String msg){
     switch (state){
         case HandShake:
             handShake(msg);
             break;
         case Idle:
             idle(msg);
             break;
         case Sending:
             sending(msg);
             break;
         case Receiving:
             receiving(msg);
             break;
         case Conformation:
             conformation(msg);

     }
 }

    public void updateState(ByteBuffer message){
        switch (state){
            case Receiving:
                receiving(message);
                break;
            default:
                System.out.println("Else hit");

        }
    }

 public void handShake(String msg){
     if(msg.equals(SSID.getSsid())){
         phoneClient.send(" Welcome member");
         state = State.Idle;
         System.out.println("Client wsa authorised");
         PhoneBatteryComponent.phoneIsConnected =true;
     } else {
         phoneClient.close(CloseFrame.REFUSE, " Authentication refused");
         PhoneBatteryComponent.phoneIsConnected =false;
     }
 }

 public void idle(String msg){
     checkQueue();
     if(msg.contains("file ")){
         phoneClient.send("file");
         fileName = msg.substring(5);
         dataType = DataType.File;
         state = State.Receiving;
     } else if (msg.equals("n")){
         phoneClient.send("n");
         dataType = DataType.Notes;
         state = State.Receiving;
     } else if (msg.equals("t")){
         phoneClient.send("t");
         dataType = DataType.TodoList;
         state = State.Receiving;
     } else if (msg.contains("Battery")){
         PhoneBatteryComponent.batteryPresent = msg;
         PhoneBatteryComponent.calculateBP(msg);
         System.out.println(msg);
     }else if (msg.startsWith("message")){
         System.out.println(msg);
         Notifications.addToReel(msg);
     }
     else {
         System.out.println("Closed con");
         phoneClient.close(CloseFrame.UNEXPECTED_CONDITION, "Invalid state");
         PhoneBatteryComponent.phoneIsConnected =false;
     }


 }

    private void sending(String msg){
        if (msg.equals("received")){
            state = State.Idle;
            System.out.println("Success: resetting back to idle");
            return;
        }
        if(msg.equals("failed")){
            state = State.Idle;
            System.out.println("failed: resetting back to idle");
//            new AlertDialog().displayCritical("Data Transfer failed", "phone failed to recived data","","failed!");
            return;
        }
    }

    public void sendFile(File file){
     if(state == State.Idle){
         phoneClient.send("file " + file.getName());
         state = State.Conformation;
         dataType = DataType.File;
         file2Send = file;
     } else {
         System.out.println("not in idle state!");
     }

    }

    private void conformation(String msg){
        System.out.println("Conformation : " + msg);
     if(dataType == DataType.File){
         if(msg.equals("file")){
             System.out.println(file2Send.getPath());
             try {
                 InputStream inputStream = new FileInputStream(file2Send.getPath());
                 byte[] data = IOUtils.readFully(inputStream,-1,false);
                 phoneClient.send(data);
                 System.out.println("Sent files over !");
                 state = State.Sending;


             } catch (Exception e) {
                 e.printStackTrace();
             }

         }

     } else if (dataType == DataType.TodoList){
         if(msg.equals("todolist")){
             System.out.println(file2Send.getPath());
             try{
                 InputStream inputStream = new FileInputStream(file2Send.getPath());
                 byte[] data = IOUtils.readFully(inputStream,-1,false);
                 phoneClient.send(data);
                 System.out.println("sent TodoList data");
                 state = State.Sending;
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     } else if (dataType == DataType.Notes){
         if(msg.equals("note")){
             try {
                 InputStream inputStream = new FileInputStream(file2Send.getPath());
                 byte[] data = IOUtils.readFully(inputStream,-1 , false);
                 phoneClient.send(data);
                 System.out.println("sent Notes data");
                 state = State.Sending;
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
    }

    private void receiving(String msg){
     state = State.Idle;
    }

    private void receiving (ByteBuffer msg) {
        if (dataType == DataType.File) {
            System.out.println("\n\n\n I got here!");
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    fileName = fileName + "Copy";
                }
                System.out.println("got files?");
                FileOutputStream outputStream = new FileOutputStream(fileName);
                outputStream.write(msg.array());
                phoneClient.send("received");
                state = State.Idle;
            } catch (IOException e) {
                phoneClient.send("failed");
                state = State.Idle;
                e.printStackTrace();
            }
    }
 }

    public void connectionAddQueue(File file){
     if(transferQueue ==null){
         transferQueue = new LinkedList<File>();
         transferQueue.add(file);
     } else {
         transferQueue.add(file);
     }
    }

    private void checkQueue(){
     if(transferQueue == null){
         return;
     }else if(transferQueue.size() > 0){
         File file = transferQueue.remove();
         sendFile(file);
     }
    }

}
