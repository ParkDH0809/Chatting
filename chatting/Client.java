package chatting;

import java.net.*;


public class Client {
    
    public static void main(String[] args) {
        try {
            String serverIP = "127.0.0.1";
            Socket socket = new Socket(serverIP, 8888);
            
            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);

            sender.start();
            receiver.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
