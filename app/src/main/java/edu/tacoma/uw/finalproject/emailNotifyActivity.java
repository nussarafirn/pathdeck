package edu.tacoma.uw.finalproject;
/**
 * This class allow user to send the notify email to all the people in the notes that
 * have been contacted in 14 days
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import edu.tacoma.uw.finalproject.model.Note;

public class emailNotifyActivity extends AppCompatActivity {
    /**
     * Access the editText to
     */
    private EditText textTo;
    /**
     * Access the editText subject
     */
    private EditText textSubject;
    /**
     * Access the editText message
     */
    private EditText textMessage;
    /**
     * contain the note list in the notes table
     */
    private List<Note> mNoteList;/**
     * access to the username that have been save
     */
    public SharedPreferences mSharedPreferences;
    /*
     * The file stores user information
     */
    public final String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";

    /**
     * Match all the variable fields with the editText in UI to access and set the text information,
     * call the mail API on the android.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_notify);
        mSharedPreferences = getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);
        textTo = findViewById(R.id.edit_text_to);
        textSubject = findViewById(R.id.edit_text_subject);
        textMessage = findViewById(R.id.edit_text_message);
        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });


    }

    /**
     * take email all people save in covid notes in 14 days and set it
     * automatically in the To email and launch the current mail box on the user device
     */
    private void sendEmail(){
        String recipientList = textTo.getText().toString();
        String[] receipients = recipientList.split(",");
        String subject = textSubject.getText().toString();
        String message = textMessage.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, receipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, ""));
    }

    /**
     * Pass in the URL to the NotesTask class to get the JsonArray
     */
    protected void onResume(){
        super.onResume();
        new NotesTask().execute(getString(R.string.get_Notes));


    }

    /**
     * class that take the urls string, read the urls and return the json array.
     */
    private class NotesTask extends AsyncTask<String, Void, String> {
        /**
         * take and read the urls to text string
         * @param urls
         * @return string
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * take the string that have been read from urls and add to the note list
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mNoteList = Note.parseNoteJson(
                            jsonObject.getString("notes"));
                    if(!mNoteList.isEmpty()){
                        //setupRecyclerView((RecyclerView) mRecyclerView);
                    }
                }
                textTo.setText(getEmailList());
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * look for all the email contained in the notes table that less than 14 days and
     * belong to current user, and put together in one text string
     * @return mEmailList
     */
    private String getEmailList() {
        List<String> emailList = new ArrayList<>();
        String username = mSharedPreferences.getString("username", null);
        for (Note note : mNoteList) {
            if(note.getUsername().equalsIgnoreCase(username) && differentDays(note.getNoteDate())) {
                emailList.add(note.getNoteEmail());
            }
            //emailList.add(note.getNoteEmail());
        }
        String mEmailList = TextUtils.join(", ", emailList);
        return mEmailList;
    }

    /**
     * Calculate the days that the notes were created to the current date,
     * and return true of the notes is less than 14
     * @param start
     * @return boolean true/false
     */
    private boolean differentDays(String start) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try{
            Date from = sdf.parse(start);
            Date current = new Date();
            long diffInMillies = Math.abs(current.getTime() - from.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff < 14){
                return true;
            }
        }catch (ParseException e){

        }
        return false;
    }

}