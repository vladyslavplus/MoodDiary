import Models.*;
import Services.JsonFileManager;
import Services.MoodDiaryService;

public class Main {
    public static void main(String[] args) {
        MoodDiaryService moodDiaryService = new MoodDiaryService(new JsonFileManager());
        ConsoleUI consoleUI = new ConsoleUI(moodDiaryService);
        consoleUI.start();
    }
}
