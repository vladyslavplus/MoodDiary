package Models;

import java.util.List;

public interface Storage {
    List<MoodEntry> loadEntries();
    void saveEntries(List<MoodEntry> entries);
}
