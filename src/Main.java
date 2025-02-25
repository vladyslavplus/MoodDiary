import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while(running) {
            System.out.println("(1) - Add record");
            System.out.println("(2) - Show all records");
            System.out.println("(3) - Edit record");
            System.out.println("(4) - Delete record");
            System.out.println("(5) - Search record");
            System.out.println("(0) - Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 0: {
                    System.out.println("You are about to go back!");
                    running = false;
                    break;
                }
                case 1: {
                    System.out.print("Enter your mood: ");
                    String mood = scanner.nextLine();
                    System.out.print("Ok...\nEnter your note: ");
                    String note = scanner.nextLine();
                    clearConsole();
                    break;
                }
                case 2: {

                    clearConsole();
                    break;
                }
                case 3: {

                    clearConsole();
                    break;
                }
                default: {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    public static void pauseBeforeClear(Scanner scanner) {
        System.out.print("Press enter to continue... ");
        scanner.nextLine();
    }
    public static void clearConsole() {
        pauseBeforeClear(new Scanner(System.in));
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
