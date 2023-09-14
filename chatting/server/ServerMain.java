package chatting.server;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server(3);
        server.start();
    }
}
