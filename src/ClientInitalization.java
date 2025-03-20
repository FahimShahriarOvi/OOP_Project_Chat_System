import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientInitalization {

    private Socket clientSocket;
    private ObjectInputStream clintReader;
    private ObjectOutputStream clintWriter;
    private String clintUserName;
    private String clintPassword;

    public ClientInitalization(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    public void initialize() throws IOException, ClassNotFoundException {

        clintWriter = new ObjectOutputStream(clientSocket.getOutputStream());
        clintReader = new ObjectInputStream(clientSocket.getInputStream());

        Scanner in = new Scanner(System.in);
        int clientChoise;
        boolean accepttheClient = false;
        Extra.welocme();

        do {
            String inputChoice = Extra.menu(in);
            try {
                clientChoise = Integer.parseInt(inputChoice);
            } catch (NumberFormatException e) {
                Extra.red();
                System.out.println("Please choose an valid number.");
                Extra.normal();
                continue;
            }

            if (!(clientChoise == 1 || clientChoise == 2)) {
                System.out.println("Your selection is wrong. Please select again:\n");
                continue;
            }

            System.out.print("\nEnter Username: ");
            clintUserName = in.next();

            System.out.print("\nEnter Password: ");
            clintPassword = in.next();

            clintWriter.writeObject(clientChoise);
            clintWriter.writeObject(clintUserName);
            clintWriter.writeObject(clintPassword);
            accepttheClient = (boolean) clintReader.readObject();

            if (!accepttheClient) {
                System.out.println("\nUsername and Password is not correct or has been used.");
                System.out.println("Please try again: \n");
            }

        } while (!accepttheClient);

    }

    public void sendMessage() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("You have entered the chat.");
            while (clientSocket.isConnected()) {
                String messagetoSend = scan.nextLine();
                clintWriter.writeObject((clintUserName + ": " + messagetoSend));
            }

            scan.close();
        } catch (IOException e) {
            shutdownEverything(clientSocket, clintReader, clintWriter);
        }
    }

    public void ListenMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String messageFromChat;

                while (clientSocket.isConnected()) {
                    try {
                        messageFromChat = (String) clintReader.readObject();

                        synchronized (this) {
                            Extra.beep();
                            if (messageFromChat.split(" ")[0].contains("Server:"))
                                Extra.red();
                            else
                                Extra.blue();
                            System.out.println(messageFromChat);
                            Extra.normal();
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        shutdownEverything(clientSocket, clintReader, clintWriter);
                    }
                }
            }

        }).start();
    }

    public void shutdownEverything(Socket clientSocket, ObjectInputStream clintReader,
            ObjectOutputStream clintWriter) {
        try {
            if (clintReader != null)
                clintReader.close();
            if (clintWriter != null)
                clintWriter.close();
            if (clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
