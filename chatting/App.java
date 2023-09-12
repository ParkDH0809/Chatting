package chatting;

import chatting.client.Client;
import chatting.server.Server;

class App {
    public static void main(String[] args) {

        Thread serverThread = new Thread() {
            @Override
            public void run() {
                Server server = new Server();
                server.doit();
            }
        };
        serverThread.start();

        
        Client client = new Client();
        client.doit();
    }
}