package chatting;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    HashMap clients;

    Server() {
        clients = new HashMap();
        Collections.synchronizedMap(clients);
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("서버가 시작되었습니다.");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            }
        } catch (Exception ignore) {}
    }

    void sendToAll(String msg) {
        Iterator it = clients.keySet().iterator();
        while(it.hasNext()) {
            try {
                DataOutputStream out = (DataOutputStream) clients.get(it.next());
                out.writeUTF(msg);
            } catch (IOException ignore) {}
        }
    }

    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ignore) {}
        }

        public void run() {
            String name = "";

            try {
                name = in.readUTF();
                sendToAll("#" + name + "님이 들어오셨습니다.");

                clients.put(name, out);
                System.out.println("현재 서버 접속자 수는 " + clients.size() + " 입니다.");

                while(in != null) {
                    sendToAll(in.readUTF());
                }
            } catch (IOException ignore) {
            } finally {
                sendToAll("#" + name + "님이 나가셨습니다.");
                clients.remove(name);
                System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] 에서 접속을 종료하였습니다.");
                System.out.println("현재 서버 접속자 수는 + " + clients.size() + " 입니다.");
            }

        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
