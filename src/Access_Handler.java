import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Access_Handler {

    private int clintChoice;
    private String clintUsername;
    private String clintPassword;
    public File file;
    Map<String, String> userinfo;

    Access_Handler(int clintChoice, String clintUsername, String clintPassword, Map<String, String> userinfo) {
        this.clintChoice = clintChoice;
        this.clintUsername = clintUsername;
        this.clintPassword = clintPassword;
        this.userinfo = userinfo;
    }

    public synchronized boolean Accept() throws IOException {
        if (clintChoice == 1) {
            if (!userinfo.containsKey(clintUsername)) {

                userinfo.put(clintUsername, clintPassword);
                DB_Handeler.register(clintUsername, clintPassword);
                return true;
            }
        }

        else if (clintChoice == 2) {
            if (userinfo.containsKey(clintUsername)) {
                if (userinfo.get(clintUsername).equals(clintPassword))
                    return true;
            }
        }

        return false;
    }

}