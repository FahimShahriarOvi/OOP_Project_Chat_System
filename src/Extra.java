import java.util.Scanner;
import java.awt.Toolkit;

public class Extra {
    public static void welocme() {
        System.out.println();
        System.out.println("*****************************************");
        System.out.println("*                                       *");
        System.out.println("*   Welcome to the group chat system.   *");
        System.out.println("*                                       *");
        System.out.println("******************************************\n");
    }

    public static void red() {
        System.out.print("\033[1;31m");
    }

    public static void blue() {
        System.out.print("\033[1;34m");
    }

    public static void normal() {
        System.out.print("\033[1;0m");
    }

    public static String menu(Scanner in) {
        System.out.println("1. Create a new account.\n");
        System.out.println("2. Log in a account.\n");
        System.out.print("Enter choise (1 or 2): ");

        return in.nextLine();

    }

    public static void beep() {
        Runnable sound1 = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
        if (sound1 != null)
            sound1.run();
    }
}
