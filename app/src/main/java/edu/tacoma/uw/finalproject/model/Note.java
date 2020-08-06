package edu.tacoma.uw.finalproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note implements Serializable {
    private String mNoteId;
    private String mNoteWho;
    private String mNotePhone;
    private String mNoteEmail;
    private String mNoteDate;
    private String mNoteLocation;

    public static final String ID = "notesid";
    public static final String Note_Who = "iwaswith";
    public static final String Note_Phone = "phone";
    public static final String Note_Email = "email";
    public static final String Note_Date = "date";
    public static final String Note_Location = "location";

    public Note(String mNoteId, String mNoteWho, String mNotePhone, String mNoteEmail, String mNoteDate, String mNoteLocation) {
        this.mNoteId = mNoteId;
        this.mNoteWho = mNoteWho;
        this.mNotePhone = mNotePhone;
        this.mNoteEmail = mNoteEmail;
        this.mNoteDate = mNoteDate;
        this.mNoteLocation = mNoteLocation;
    }

    public static List<Note> parseNoteJson(String courseJson) throws JSONException {
        List<Note> courseList = new ArrayList<>();

        if (courseJson != null) {

            JSONArray arr = new JSONArray(courseJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Note course = new Note(obj.getString(Note.ID), obj.getString(Note.Note_Who), obj.getString(Note.Note_Phone),
                        obj.getString(Note.Note_Email), obj.getString(Note.Note_Date), obj.getString(Note.Note_Location));
                courseList.add(course);
            }
        }

        return courseList;
    }



    public String getNoteId() {
        return mNoteId;
    }

    public String getNoteWho() {
        return mNoteWho;
    }

    public String getNotePhone() {
        return mNotePhone;
    }

    public String getNoteEmail() {
        return mNoteEmail;
    }

    public String getNoteDate() {
        return mNoteDate;
    }

    public String getNoteLocation() {
        return mNoteLocation;
    }

    public void setNoteId(String mNoteId) {
        this.mNoteId = mNoteId;
    }

    public void setNoteWho(String mNoteWho) {
        this.mNoteWho = mNoteWho;
    }

    public void setNotePhone(String mNotePhone) {
        this.mNotePhone = mNotePhone;
    }

    public void setNoteEmail(String mNoteEmail) {
        this.mNoteEmail = mNoteEmail;
    }

    public void setNoteDate(String mNoteDate) {
        this.mNoteDate = mNoteDate;
    }

    public void setNoteLocation(String mNoteLocation) {
        this.mNoteLocation = mNoteLocation;
    }
}
