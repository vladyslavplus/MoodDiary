package Services;

import Models.MoodEntry;
import Models.MoodType;
import Models.Storage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class MoodDiaryService {
    private final List<MoodEntry> entries;
    private final Storage storage;

    public MoodDiaryService(Storage storage) {
        this.storage = storage;
        this.entries = storage.loadEntries();

        int maxId = entries.stream().mapToInt(MoodEntry::getId).max().orElse(0);
        MoodEntry.setIdCounter(maxId + 1);
    }

    public void addEntry(MoodType mood, String note){
        entries.add(new MoodEntry(mood, note, LocalDate.now()));
        save();
    }

    public void viewEntries() {
        entries.stream()
                .sorted(Comparator.comparing(MoodEntry::getDate))
                .forEach(System.out::println);
    }

    public void editEntry(int id, MoodType newMood, String newNote){
        for(MoodEntry entry : entries){
            if(entry.getId() == id){
                entry.setMood(newMood);
                entry.setNote(newNote);
                save();
                return;
            }
        }
        System.out.println("No such record exists");
    }

    public void deleteEntry(int id){
        entries.removeIf(entry -> entry.getId() == id);
        save();
    }

    public void searchEntries(String key)
    {
        System.out.println("Searching for " + key + "....");

        entries.stream()
                .filter(entry -> {
                    boolean matchMood = entry.getMood().name().toLowerCase().contains(key.toLowerCase());
                    boolean matchNote = entry.getNote().toLowerCase().contains(key.toLowerCase());
                    boolean matchDate = entry.getDate().toString().toLowerCase().contains(key.toLowerCase());

                    return matchMood || matchNote || matchDate;
                })
                .forEach(System.out::println);
    }

    public void sortEntries(Comparator<MoodEntry> comparator){
        entries.sort(comparator);
        viewEntries();
    }

    private void save() {
        storage.saveEntries(entries);
    }
}
