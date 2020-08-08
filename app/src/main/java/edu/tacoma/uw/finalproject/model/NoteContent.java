package edu.tacoma.uw.finalproject.model;
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * @author Kieu Trinh
 * @version summer 2020
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class NoteContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Note> ITEMS = new ArrayList<Note>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Note> ITEM_MAP = new HashMap<String, Note>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNoteItem(i));
        }
    }

    private static void addItem(Note item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getNoteId(), item);
    }

    private static Note createNoteItem(int position) {
        return new Note("Id" + position, "who" + position,"username" + position ,"Phone" + position,
                "Email" + position, "Date" + position, "Location" + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}
