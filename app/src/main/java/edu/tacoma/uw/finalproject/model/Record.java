package edu.tacoma.uw.finalproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the model card class using to present information about temperature,
 * symptoms, and etc
 *
 * Author: Firn TIeanklin
 * Date: 7/18/20
 */
public class Record implements Serializable {
    /**
     * present the id field in Records table
     */
    private String id;

    /**
     * present the user's username in Records table
     */
    private String username;

    /**
     * present the user's body temperature in Records table
     */
    private double temp;

    /**
     * present the user's symptoms  in Records table
     */
    private String symp;
    /**
     * present the user's testing result for COVID in Records table if applicable
     */
    private String rec_test;
    /**
     * present the user's record date in Records table
     */
    private String rec_date;

    public static final String REC_ID = "recordid";
    public static final String REC_USERNAME = "username";
    public static final double REC_TEMP = 0.0;
    public static final String REC_SYMP = "symp";
    public static final String REC_TEST = "rec_test";
    public static final String REC_DATE = "rec_date";

    /**
     * Constructor contructs all the fields in Record class
     *
     * @param id
     * @param temp
     * @param symp
     * @param rec_test
     * @param rec_date
     */
    public Record(String id, String username, double temp, String symp, String rec_test, String rec_date) {
        this.id = id;
        this.username = username;
        this.temp = temp;
        this.symp = symp;
        this.rec_test = rec_test;
        this.rec_date = rec_date;
    }


    /**
     * make the file to JSONArray and take the JSON element, store the information the note class return list
     * of all members in the Records table
     *
     * @param recJson
     * @return
     * @throws JSONException
     */
    public static List<Record> parseRecJson(String recJson) throws JSONException {
        List<Record> recordList = new ArrayList<>();

        if (recJson != null) {

            JSONArray arr = new JSONArray(recJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                //using note fields to get JSON strong object
                Record record = new Record(obj.getString(Record.REC_ID), obj.getString(Record.REC_USERNAME),
                        obj.getDouble(String.valueOf(Record.REC_TEMP)), obj.getString(Record.REC_SYMP),
                        obj.getString(Record.REC_TEST), obj.getString(Record.REC_DATE));
                recordList.add(record);
            }
        }

        return recordList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecId() {
        return id;
    }

    public void setRecId(String id) {
        this.id = id;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getSymp() {
        return symp;
    }

    public void setSymp(String symp) {
        this.symp = symp;
    }

    public String getRecTest() {
        return rec_test;
    }

    public void setRecTest(String rec_test) {
        this.rec_test = rec_test;
    }

    public String getRecDate() {
        return rec_date;
    }

    public void setRecDate(String rec_date) {
        this.rec_date = rec_date;
    }
}