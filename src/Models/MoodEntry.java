package Models;

import java.time.LocalDate;

public class MoodEntry {
    private static int idCounter = 1;

    private final int id;
    private MoodType mood;
    private String note;
    private final LocalDate date;

    public MoodEntry(MoodType mood, String note, LocalDate date) {
        this.id = idCounter++;
        this.mood = mood;
        this.note = note;
        this.date = date;
    }


    public int getId() {
        return id;
    }
    public MoodType getMood() {
        return mood;
    }
    public String getNote() {
        return note;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setMood(MoodType mood) {
        this.mood = mood;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public static void setIdCounter(int newIdCounter) {
        idCounter = newIdCounter;
    }

    @Override
    public String toString() {
        return id + " | " + mood + " | " + note + " | " + date;
    }
}
