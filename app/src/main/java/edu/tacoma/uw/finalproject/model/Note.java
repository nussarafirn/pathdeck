package edu.tacoma.uw.finalproject.model;
/**
 * Provide the string fields to get the data from notes
 * @author Kieu Trinh
 * @version summer 2020
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Note implements Serializable {
    /**
     * present the id field in note table
     */
    private String mNoteId;
    /**
     * present the person's name field in note table
     */
    private String mNoteWho;

    private String mUsername;
    /**
     * present the phone field in note table
     */
    private String mNotePhone;
    /**
     * present the  email in note table
     */
    private String mNoteEmail;
    /**
     * present the date field in note table
     */
    private String mNoteDate;
    /**
     * present the loaction field in note table
     */
    private String mNoteLocation;
    /**
     * the id field in note table
     */
    public static final String ID = "notesid";

    public static final String Note_username = "username";
    /**
     * the person's name field in note table
     */
    public static final String Note_Who = "iwaswith";
    /**
     * the phone field in note table
     */
    public static final String Note_Phone = "phone";
    /**
     * the  email in note table
     */
    public static final String Note_Email = "email";
    /**
     * the date field in note table
     */
    public static final String Note_Date = "date";
    /**
     * the location field in note table
     */
    public static final String Note_Location = "location";

    /**
     * constructor to initialize all the fields
     * @param mNoteId
     * @param mNoteWho
     * @param mNotePhone
     * @param mNoteEmail
     * @param mNoteDate
     * @param mNoteLocation
     */
    public Note(String mNoteId, String mNoteWho, String username, String mNotePhone, String mNoteEmail, String mNoteDate, String mNoteLocation) {
        if(!mNoteWho.isEmpty() && !mNoteLocation.isEmpty()
                && mNoteEmail.contains("@")
                && mNotePhone.length() == 10 && !username.isEmpty()){
            this.mNoteId = mNoteId;
            this.mNoteWho = mNoteWho;
            this.mUsername = username;
            this.mNotePhone = mNotePhone;
            this.mNoteEmail = mNoteEmail;
            this.mNoteDate = mNoteDate;
            this.mNoteLocation = mNoteLocation;
        }else{
            throw new IllegalArgumentException();
        }

    }

    /**
     * make the file to JSONArray and take the JSON element, store the information the note classreturn list
     * of all members in notes table
     * @param courseJson
     * @return
     * @throws JSONException
     */
    public static List<Note> parseNoteJson(String courseJson) throws JSONException {
        List<Note> noteList = new ArrayList<>();

        if (courseJson != null) {

            JSONArray arr = new JSONArray(courseJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                //using note fields to get JSON strong object
                Note course = new Note(obj.getString(Note.ID), obj.getString(Note.Note_Who),obj.getString(Note.Note_username),obj.getString(Note.Note_Phone),
                        obj.getString(Note.Note_Email), obj.getString(Note.Note_Date), obj.getString(Note.Note_Location));
                noteList.add(course);
            }
        }

        return noteList;
    }


    /**
     * return the Id of the note member
     * @return mNoteId
     */
    public String getNoteId() {
        return mNoteId;
    }
    /**
     * return the iwaswith of the note member
     * @return mNoteWHo
     */
    public String getNoteWho() {
        return mNoteWho;
    }

    public String getUsername() {
        return mUsername;
    }
    /**
     * return the phone data of the note member
     * @return mNotePhone
     */
    public String getNotePhone() {
        return mNotePhone;
    }
    /**
     * return the email of the note member
     * @return mNoteEmail
     */
    public String getNoteEmail() {
        return mNoteEmail;
    }
    /**
     * return the date data of the note member
     * @return mNoteDate
     */
    public String getNoteDate() {
        return mNoteDate;
    }
    /**
     * get the loaction of the note member
     * @return mNoteLocation
     */
    public String getNoteLocation() {
        return mNoteLocation;
    }

    /**
     * set the ID field in the note table
     * @param mNoteId
     */
    public void setNoteId(String mNoteId) {
        this.mNoteId = mNoteId;
    }

    /**
     * set the iwaswith field in the note table
     * @param mNoteWho
     */
    public void setNoteWho(String mNoteWho) {
        if(!mNoteWho.isEmpty()){
            this.mNoteWho = mNoteWho;
        }else{
            throw new IllegalArgumentException("name cannot be empty");
        }

    }


    /**
     * set the phone filed in the note table
     * @param mNotePhone
     */
    public void setNotePhone(String mNotePhone) {
        if(mNotePhone.isEmpty() && mNotePhone.length() != 10){
            throw new IllegalArgumentException("Enter 10 digits number format XXXXXXXXXX");
        }else {
            this.mNotePhone = mNotePhone;
        }

    }

    /**
     * set the email field in the note table
     * @param mNoteEmail
     */
    public void setNoteEmail(String mNoteEmail) {
        if(mNoteEmail.contains("@") && !mNoteEmail.isEmpty()){
            this.mNoteEmail = mNoteEmail;
        }else{
            throw new IllegalArgumentException("Invalid Email");
        }

    }

    /**
     * set the date field in the note table
     * @param mNoteDate
     */
    public void setNoteDate(String mNoteDate) {
        this.mNoteDate = mNoteDate;
    }

    /**
     * set the location field in the note table
     * @param mNoteLocation
     */
    public void setNoteLocation(String mNoteLocation) {
        if (mNoteLocation.isEmpty()) {
            throw new IllegalArgumentException("location cannot be empty");
        } else {
            this.mNoteLocation = mNoteLocation;

        }
    }
}


