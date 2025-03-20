import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class Client_Handler implements Runnable {

    public static ArrayList<Client_Handler> clientHandles = new ArrayList<>();
    private Socket serverSocket;
    private ObjectInputStream serverReader;
    private ObjectOutputStream serverWritter;
    private String clientUsername;
    private String clientPassword;
    private int clintChoice;
    public boolean accepttheClient;
    public int loginCount = 0;
    private Map<String, String> userinfo;

    public Client_Handler(Socket serverSocket, Map<String, String> userMap) throws IOException {
        this.serverSocket = serverSocket;
        clientHandles.add(this);
        userinfo = userMap;
    }

    @Override
    public void run() {

        try {
            serverReader = new ObjectInputStream(serverSocket.getInputStream());
            serverWritter = new ObjectOutputStream(serverSocket.getOutputStream());
        } catch (IOException e) {
            shutdownEverything(serverSocket, serverReader, serverWritter);
        }
        do {
            try {
                clintChoice = (int) serverReader.readObject();
                clientUsername = serverReader.readObject().toString();
                clientPassword = serverReader.readObject().toString();

                for (Client_Handler client_Handler : clientHandles) {
                    if (client_Handler.clientUsername.equals(clientUsername))
                        loginCount++;
                }

                Access_Handler accessHandle = new Access_Handler(clintChoice, clientUsername, clientPassword, userinfo);

                accepttheClient = accessHandle.Accept();

                if (loginCount != 1) {
                    accepttheClient = false;
                    loginCount = 0;
                }

                serverWritter.writeObject(accepttheClient);

            } catch (IOException | ClassNotFoundException e) {
                shutdownEverything(serverSocket, serverReader, serverWritter);
            }
        } while (!accepttheClient);

        loginCount = 0;

        sendMessageToAll("Server: " + clientUsername + " has entered the chat.");

        Server_Printer serverPrint = new Server_Printer(clintChoice, clientUsername);
        serverPrint.print();

        String messageFromClient;

        while (serverSocket.isConnected()) {
            try {
                messageFromClient = (String) serverReader.readObject();
                sendMessageToAll(messageFromClient);
            } catch (IOException | ClassNotFoundException e) {
                shutdownEverything(serverSocket, serverReader, serverWritter);
                break;
            }
        }
    }

    public void sendMessageToAll(String messageFromClient) {
        for (Client_Handler client_Handler : clientHandles) {
            try {
                if (!client_Handler.clientUsername.equals(clientUsername)) {
                    client_Handler.serverWritter.writeObject(messageFromClient);
                }
            } catch (IOException e) {
                shutdownEverything(serverSocket, serverReader, serverWritter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandles.remove(this);
        sendMessageToAll("Server: " + clientUsername + " has left the chart.");
    }

    public void shutdownEverything(Socket mySocket, ObjectInputStream serverReader,
            ObjectOutputStream serverWritter) {
        removeClientHandler();
        try {
            if (serverReader != null)
                serverReader.close();
            if (serverWritter != null)
                serverWritter.close();
            if (mySocket != null)
                mySocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
