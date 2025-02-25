package Services;
import Models.MoodEntry;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import Models.Storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonFileManager implements Storage {
    private static final String FILE_NAME = "mood_entries.json";
    private final Gson gson;

    public JsonFileManager() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.toString());
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                        return LocalDate.parse(json.getAsString());
                    }
                })
                .setPrettyPrinting()
                .create();
    }

    @Override
    public List<MoodEntry> loadEntries() {
        try(FileReader fileReader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<MoodEntry>>() {}.getType();
            List<MoodEntry> entries = gson.fromJson(fileReader, listType);
            if (entries == null) {
                return new ArrayList<>();
            }
            return entries;
        } catch (IOException e) {
            return new java.util.ArrayList<>();
        }
    }

    @Override
    public void saveEntries(List<MoodEntry> entries) {
        try(FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            gson.toJson(entries, fileWriter);
        } catch (IOException e) {
            System.out.println(FILE_NAME + " could not be saved");
        }
    }
}
