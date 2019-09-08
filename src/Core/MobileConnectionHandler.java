package Core;


import UI.MainScene;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class MobileConnectionHandler extends WebSocketServer  {


    private Set<WebSocket>accpetedConn;
    private ConnectionHandler connectionHandler;
    private MainScene ui;

    public MobileConnectionHandler(int port, MainScene mainScene) throws UnknownHostException{
        super( new InetSocketAddress( port));
        ui = mainScene;
    }

    public MobileConnectionHandler(InetSocketAddress address){
        super(address);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        //webSocket.send("Welcome to the sever!");
        //broadcast("new Connection" + clientHandshake.getResourceDescriptor());
        System.out.println(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " connected to the pc!");
        connectionHandler = new ConnectionHandler(webSocket);
        if(ui != null) ui.networkISActive();

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        broadcast(webSocket + " has disconnected form the pc");
        System.out.println( webSocket + " Disconnected from the pc!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
       // broadcast(s);
       // System.out.println(webSocket + " sent : " + s);
       connectionHandler.updateState(s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        if(webSocket != null){
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        System.out.println(" Server created!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
        System.out.println("Adress: " + this.getAddress() + " Port:" + this.getAddress().getPort());
    }

    public void initSever() throws InterruptedException, IOException {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
           System.out.println(inetAddress.getHostAddress().toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        accpetedConn = new HashSet<WebSocket>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    MobileConnectionHandler.this.start();
                    while (true) {
                        String in = br.readLine();
                        MobileConnectionHandler.this.broadcast(in);
                        if (in.equals("exit")) {
                            MobileConnectionHandler.this.stop(1000);
                            break;
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();



    }

    public ConnectionHandler getConnectionHandler(){
        return connectionHandler;
    }
}
