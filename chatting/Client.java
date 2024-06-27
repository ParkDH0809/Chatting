package chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public class Client {
    
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("이름을 입력해주세요");

            String name = scanner.nextLine();
            String serverIp = "127.0.0.1";

            Socket socket = new Socket(serverIp, 7777);
            System.out.println("성공적으로 연결되었습니다.");
            Thread sender = new Thread(new ClientSender(socket, name));
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();
        } catch (IOException ignored) {}
    }

    static class ClientSender extends Thread {

        Socket socket;
        DataOutputStream out;
        String name;

        ClientSender(Socket socket, String name) {
            this.socket = socket;
            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            } catch (Exception ignored) {}
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                if(out != null) {
                    out.writeUTF(name);
                }

                while(out!=null) {
                    out.writeUTF("[" + name + "]" + scanner.nextLine());
                }
            } catch (IOException ignored) {}
        }
    }

    static class ClientReceiver extends Thread {
        Socket socket;
        DataInputStream in;

        ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
            } catch(IOException ignored) {}
        }

        public void run() {
            while(in != null) {
                try {
                    System.out.println(in.readUTF());
                } catch (IOException ignored) {}
            }
        }
    }
}
