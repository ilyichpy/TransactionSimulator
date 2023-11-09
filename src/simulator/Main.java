package simulator;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        boolean mod = false;
        if (args.length > 0 && args[0].equals("--profile=dev")) {
            mod = true;
        }
        new Menu().run(mod);
        sc.close();
    }
}