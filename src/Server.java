import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        DB_Handeler dbh = new DB_Handeler("DB", "LoginInfo.txt");
        
        Map<String, String> userinfo = new ConcurrentHashMap<>();
        
        try {
            dbh.load(userinfo);
        } catch (Exception e) {
            System.out.println("Server started unsuccssfull.");
            System.exit(1);
        }

        serverSocket = new ServerSocket(22223);

        System.out.println("Server Started Succesfully....");

        try {

            System.out.println("Waiting for clients.....");
            while (true) {

                Socket mySocket = serverSocket.accept();

                Client_Handler clientHandle = new Client_Handler(mySocket, userinfo);

                Thread myThread = new Thread(clientHandle);
                myThread.start();

            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public static void closeServerSocket() {
        try {
            if (serverSocket != null)
                serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
