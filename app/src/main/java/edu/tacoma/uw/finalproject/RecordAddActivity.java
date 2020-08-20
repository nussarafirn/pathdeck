package edu.tacoma.uw.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.finalproject.model.Record;

public class RecordAddActivity extends AppCompatActivity implements HealthAddFragment.HealthAddListener {
    public static final String ADD_REC = "ADD_REC";
    private JSONObject recJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.health_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//
//        // launch fragment
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new HealthAddFragment())
//                .commit();
    }


    @Override
    public void addHealth(Record record) {
        StringBuilder url = new StringBuilder(getString(R.string.add_record));
        recJSON = new JSONObject();

        Toast.makeText(this, "Here"
                ,
                Toast.LENGTH_SHORT).show();

        try {
            recJSON.put(Record.REC_USERNAME, record.getUsername());
            recJSON.put(Record.REC_TEMP, record.getTemp());
            recJSON.put(Record.REC_SYMP, record.getSymp());
            recJSON.put(Record.REC_TEST, record.getUsername());
            recJSON.put(Record.REC_DATE, record.getRecDate());
           new RecordAddAsyncTask().execute(url.toString());

            Toast.makeText(this, "Here2"
                    ,
                    Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {

            Toast.makeText(this, "Error with JSON creation on update health record"
                            + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class RecordAddAsyncTask extends AsyncTask<String, Void, String> {
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
                    Log.i(ADD_REC, recJSON.toString());
                    wr.write(recJSON.toString());
                    wr.flush();
                    wr.close();

                    Log.i(ADD_REC, "HEEEEERRRRRRRRRRRE");

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to update the health record, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to update the health record")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Health record updated successfully"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Health record couldn't be updated: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_REC, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on updating health record"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(ADD_REC, e.getMessage());
            }
        }

    }
}

