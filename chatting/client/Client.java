package chatting.client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    
    public void doit() {
        try {
            String serverIP = "127.0.0.1";
            Socket socket = new Socket(serverIP, 8888);

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);

            System.out.println(dis.readUTF());

            dis.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
