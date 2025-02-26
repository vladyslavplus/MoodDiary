package Models;

import Services.MoodDiaryService;
import java.util.Comparator;
import java.util.Scanner;

public class ConsoleUI {
    private final MoodDiaryService moodDiaryService;
    private final Scanner scanner;

    public ConsoleUI(MoodDiaryService moodDiaryService) {
        this.moodDiaryService = moodDiaryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("(1) - Add record");
            System.out.println("(2) - Show all records");
            System.out.println("(3) - Edit record");
            System.out.println("(4) - Delete record");
            System.out.println("(5) - Search record");
            System.out.println("(6) - Sort records");
            System.out.println("(0) - Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    System.out.println("Bye!!");
                    running = false;
                }
                case 1 -> addEntry();
                case 2 -> viewEntries();
                case 3 -> editEntry();
                case 4 -> deleteEntry();
                case 5 -> searchEntries();
                case 6 -> sortEntries();
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addEntry() {
        System.out.println("Choose your mood:");
        for (MoodType type : MoodType.values()) {
            System.out.println("- " + type);
        }

        MoodType mood = null;
        while (true) {
            System.out.print("Enter your mood: ");
            String moodInput = scanner.nextLine().toUpperCase();
            try {
                mood = MoodType.valueOf(moodInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid mood! Try again!");
            }
        }

        System.out.print("Enter your note: ");
        String note = scanner.nextLine();

        moodDiaryService.addEntry(mood, note);
        System.out.println("Entry added successfully!");
        clearConsole();
    }

    private void viewEntries() {
        moodDiaryService.viewEntries();
        clearConsole();
    }

    private void editEntry() {
        System.out.print("Enter the ID of the entry you would like to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Choose a new mood:");
        for (MoodType type : MoodType.values()) {
            System.out.println("- " + type);
        }

        MoodType newMood = null;
        while (true) {
            System.out.print("Enter new mood: ");
            String moodInput = scanner.nextLine().toUpperCase();
            try {
                newMood = MoodType.valueOf(moodInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid mood! Try again!");
            }
        }

        System.out.print("Enter the new note: ");
        String note = scanner.nextLine();

        moodDiaryService.editEntry(id, newMood, note);
        System.out.println("Entry edited successfully!");
        clearConsole();
    }

    private void deleteEntry() {
        System.out.print("Enter the ID of the entry you would like to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        moodDiaryService.deleteEntry(id);
        System.out.println("Entry deleted successfully!");
        clearConsole();
    }

    private void searchEntries() {
        System.out.print("Enter a key to search: ");
        String key = scanner.nextLine();
        moodDiaryService.searchEntries(key);
        clearConsole();
    }

    private void sortEntries() {
        System.out.print("Sort by (id/mood/note/date): ");
        String sortByInput = scanner.nextLine().toUpperCase();

        MoodEntryFields sortBy;
        try {
            sortBy = MoodEntryFields.valueOf(sortByInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid sortBy! Try again!");
            return;
        }

        System.out.print("Order by (asc/desc): ");
        String orderInput = scanner.nextLine().toUpperCase();

        SortOrder order;
        try {
            order = SortOrder.valueOf(orderInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid order! Try again!");
            return;
        }

        Comparator<MoodEntry> comparator = switch (sortBy) {
            case ID -> Comparator.comparing(MoodEntry::getId);
            case MOOD -> Comparator.comparing(entry -> entry.getMood().name());
            case NOTE -> Comparator.comparing(entry -> entry.getNote().toLowerCase());
            case DATE -> Comparator.comparing(MoodEntry::getDate);
        };

        if (order == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        moodDiaryService.sortEntries(comparator);
        clearConsole();
    }

    private void pauseBeforeClear() {
        System.out.print("Press enter to continue... ");
        scanner.nextLine();
    }

    private void clearConsole() {
        pauseBeforeClear();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
