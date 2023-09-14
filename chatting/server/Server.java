package chatting.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class Server implements Runnable {

    ServerSocket serverSocket;
    Thread[] threadArr;

    Server(int n) {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("서버 준비 완료");
        } catch (IOException e) { e.printStackTrace(); }
        threadArr = new Thread[n];
    }

    public void run() {
        while(true) {
            try {
                System.out.println(Thread.currentThread().getName() + "대기중");
                
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + "로부터 연결 요청이 들어왔습니다.");
                
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);

                dos.writeUTF("[Server] Test Message");
                
                dos.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        for(int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread(this);
            threadArr[i].start();
        }
    }
}
