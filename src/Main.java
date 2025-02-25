import Models.MoodType;
import Models.Storage;
import Services.JsonFileManager;
import Services.MoodDiaryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MoodDiaryService moodDiaryService = new MoodDiaryService(new JsonFileManager());
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
                case 0 -> {
                    System.out.print("Bye!!");
                    running = false;
                    break;
                }
                case 1 -> addEntry(moodDiaryService, scanner);
                case 2 ->  {
                    moodDiaryService.viewEntries();
                    clearConsole();
                }
                case 3 -> editEntry(moodDiaryService, scanner);
                case 4 -> deleteEntry(moodDiaryService, scanner);
                case 5 -> searchEntries(moodDiaryService, scanner);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void addEntry(MoodDiaryService moodDiaryService, Scanner scanner) {
        System.out.println("Choose your mood: ");
        for(MoodType type : MoodType.values()) {
            System.out.println("- " + type);
        }

        MoodType mood = null;
        while(mood == null) {
            System.out.print("Enter your mood: ");
            String moodInput = scanner.nextLine().toUpperCase();
            try {
                mood = MoodType.valueOf(moodInput);
                break;
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid mood! Try again!");
            }
        }
        System.out.print("Ok...\nEnter your note: ");
        String note = scanner.nextLine();
        moodDiaryService.addEntry(mood, note);
        System.out.println("Entry added successfully!");
        clearConsole();
    }
    private static void editEntry(MoodDiaryService moodDiaryService, Scanner scanner) {
        System.out.print("Enter the ID of the entry you would like to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Choose a new mood:");
        for (MoodType type : MoodType.values()) {
            System.out.println("- " + type);
        }

        MoodType newMood = null;
        while(newMood == null) {
            System.out.print("Enter new mood: ");
            String moodInput = scanner.nextLine().toUpperCase();
            try {
                newMood = MoodType.valueOf(moodInput);
                break;
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid mood! Try again!");
            }
        }
        System.out.print("Enter the new note: ");
        String note = scanner.nextLine();

        moodDiaryService.editEntry(id, newMood, note);
        System.out.println("Entry edited successfully!");
        clearConsole();
    }
    private static void deleteEntry(MoodDiaryService moodDiaryService, Scanner scanner) {
        System.out.print("Enter the ID of the entry you would like to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        moodDiaryService.deleteEntry(id);
        System.out.println("Entry deleted successfully!");
        clearConsole();
    }
    private static void searchEntries(MoodDiaryService moodDiaryService, Scanner scanner) {
        System.out.print("Enter a key to search: ");
        String key = scanner.nextLine();
        moodDiaryService.searchEntries(key);
        clearConsole();
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
