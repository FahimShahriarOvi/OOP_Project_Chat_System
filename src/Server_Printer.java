import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server_Printer {

    private int clientChoise;
    private String clientUsername;

    public Server_Printer(int clientChoise, String clientUsername) {
        this.clientChoise = clientChoise;
        this.clientUsername = clientUsername;
    }

    void print() {

        try {
            File file = new File("DB");
            file.mkdir();

            File data = new File("DB/log.txt");
            data.createNewFile();

            FileWriter myWriter = new FileWriter(data, true);

            if (clientChoise == 1) {
                System.out.println(
                        clientUsername + " has created a account in this chat at " + getCurrentDateTimeInfo() + ".");
                
                myWriter.write(
                        clientUsername + " has created a account in this chat at " + getCurrentDateTimeInfo() + ".\n");
            
            } else if (clientChoise == 2) {
               
                System.out.println(clientUsername + " has logged in this chat at " + getCurrentDateTimeInfo() + ".");
                
                myWriter.write(clientUsername + " has logged in this chat at " + getCurrentDateTimeInfo() + ".\n");
            }

            myWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getCurrentDateTimeInfo() {
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfWeek() + " " + now.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) + " "
                + " " + now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
