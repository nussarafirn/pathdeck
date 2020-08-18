package edu.tacoma.uw.finalproject;

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
    private EditText textTo;
    private EditText textSubject;
    private EditText textMessage;
    private List<Note> mNoteList;
    public SharedPreferences mSharedPreferences;
    public final String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";

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
                //Toast.makeText(emailNotifyActivity.this, "Hello" + getEmailList(), Toast.LENGTH_SHORT).show();
            }
        });


    }


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
    protected void onResume(){
        super.onResume();
        new NotesTask().execute(getString(R.string.get_Notes));
        //setupRecyclerView(mRecyclerView);

    }
    private class NotesTask extends AsyncTask<String, Void, String> {

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