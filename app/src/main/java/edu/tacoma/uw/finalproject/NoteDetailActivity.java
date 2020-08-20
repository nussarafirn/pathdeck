package edu.tacoma.uw.finalproject;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link NoteListActivity}.
 * @author kieutrinh
 * @version summer2020
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.finalproject.model.Note;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link NoteListActivity}.
 */
public class NoteDetailActivity extends AppCompatActivity implements NoteAddFragment.AddListener {
    public static final String ADD_NOTE = "ADD_NOTE";
    private JSONObject mNoteJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            if (getIntent().getSerializableExtra(NoteDetailFragment.ARG_ITEM_ID) != null){
                arguments.putSerializable(NoteDetailFragment.ARG_ITEM_ID,
                        getIntent().getSerializableExtra(NoteDetailFragment.ARG_ITEM_ID));
                NoteDetailFragment fragment = new NoteDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, fragment)
                        .commit();
            }else if (getIntent().getBooleanExtra(NoteDetailActivity.ADD_NOTE, false)){
                NoteAddFragment fragment = new NoteAddFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, fragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, NoteListActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * save all information to the JSON object and pass the urls to add the information
     * to the backend table
     * @param note
     */
    @Override
    public void addNote(Note note) {
        StringBuilder url = new StringBuilder(getString(R.string.add_Notes));
        mNoteJSON = new JSONObject();
        try{
            mNoteJSON.put(Note.Note_Who, note.getNoteWho());
            mNoteJSON.put("Username", note.getUsername());
            mNoteJSON.put(Note.Note_Phone, note.getNotePhone());
            mNoteJSON.put(Note.Note_Email, note.getNoteEmail());
            mNoteJSON.put(Note.Note_Date, note.getNoteDate());
            mNoteJSON.put(Note.Note_Location, note.getNoteLocation());
            new AddNoteAsyncTask().execute(url.toString());
        } catch(JSONException e){
            Toast.makeText(this, "Error with JSON creation on adding note " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Take the urls and read all the urls to text string and use that text string
     * to get all the notes information in JSONObject to store in notes list
     */
    private class AddNoteAsyncTask extends AsyncTask<String, Void, String> {
        /**
         * read the urls and request for the Post and setup all the information
         * in an array to be ready for adding into the table
         * @param urls
         * @return String
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());

                    // For Debugging
                    Log.i(ADD_NOTE, mNoteJSON.toString());
                    wr.write(mNoteJSON.toString());
                    wr.flush();
                    wr.close();
                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add the new course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * Take the all data have been read and add to the JsonObject
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to add the new course")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Notes Added successfully"
                            , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Course couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_NOTE, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding course"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(ADD_NOTE, e.getMessage());
            }
        }
    }
}