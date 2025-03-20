import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("127.0.0.1", 22223);
        ClientInitalization client = new ClientInitalization(clientSocket);
        client.initialize();
        client.ListenMessage();
        client.sendMessage();
    }
}
