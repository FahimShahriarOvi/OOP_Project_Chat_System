import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DB_Handeler {
    private final String dir;
    private final String file;

    private final String path;
    private static String spath;

    public DB_Handeler(String dir, String file) {
        System.out.println("Starting Server.");
        this.dir = dir;
        this.file = file;
        this.path = this.dir + "/" + this.file;

        File d = new File(this.dir);
        spath = path;
        d.mkdir();
    }

    public void load(Map<String, String> map) throws IOException {
        System.out.println("Please Wait..");

        File loginInfo = new File(path);
        if (!loginInfo.exists()) {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write("Username Password\n");
            fileWriter.close();
        }

        Scanner fileScan = new Scanner(loginInfo);

        while (fileScan.hasNextLine()) {
            String[] info = fileScan.nextLine().split(" ");
            if (info.length == 2) {
                map.put(info[0], info[1]);
            }
        }
        fileScan.close();
        
    }

    public static void register(String un, String up) throws IOException {
        File file = new File(spath);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(un + " " + up + "\n");
        fileWriter.close();
    }
}
