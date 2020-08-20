package edu.tacoma.uw.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.tacoma.uw.finalproject.authenticate.LoginFragment;
import edu.tacoma.uw.finalproject.model.Note;
import edu.tacoma.uw.finalproject.model.Record;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthFragment extends Fragment {

    private CardView card_test;
    private List<Record> mRecordList;
    public SharedPreferences mSharedPreferences;
    public final String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";
    private TextView symptom;
    private TextView temp;
    private TextView testResult;
    private TextView tempState;
    private String username;
    public HealthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static HealthFragment newInstance() {
        HealthFragment fragment = new HealthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        card_test = view.findViewById(R.id.card_test);
        temp = view.findViewById(R.id.temp_text);
        symptom = view.findViewById(R.id.symptom_text);
        testResult = view.findViewById(R.id.test_text);
        tempState = view.findViewById(R.id.bodyState_text);

        mSharedPreferences = this.getActivity().getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);
        username = mSharedPreferences.getString("username", null);

        card_test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), emailNotifyActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.health_fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                launchHealthAddFragment();
            }
        });
        return view;
    }
    public void launchHealthAddFragment(){
        HealthAddFragment healthAddFragment = new HealthAddFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.health_item_detail_container, healthAddFragment).commit();
    }
    @Override
    public void onResume(){
        super.onResume();

        new RecordTask().execute(getString(R.string.get_record));
        //setupRecyclerView(mRecyclerView);

    }
    private class RecordTask extends AsyncTask<String, Void, String> {

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
                Toast.makeText(getActivity(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mRecordList = Record.parseRecJson(
                            jsonObject.getString("records"));
                    if(!mRecordList.isEmpty()){
                        //setupRecyclerView((RecyclerView) mRecyclerView);
                    }
                }
                temp.setText(getTemp() +" °F");
                symptom.setText(getSymp());
                testResult.setText(getTestResult());
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getTemp() {
        List<String> tempList = new ArrayList<>();

        for (Record each : mRecordList) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                tempList.add(String.valueOf(each.getTemp()));
            }

            //emailList.add(note.getNoteEmail());
        }

        return tempList.get(tempList.size()-1);
    }
    private String getSymp() {
        //String username = mSharedPreferences.getString("username", null);
        List<String> sympList = new ArrayList<>();
        for (Record each : mRecordList) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                sympList.add(each.getSymp());
            }
        }
        return sympList.get(sympList.size()-1);
    }
    private String getTestResult() {
        List<String> testRecList = new ArrayList<>();
        for (Record each : mRecordList) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                testRecList.add(each.getRecTest());
            }
        }
        return testRecList.get(testRecList.size()-1);
    }
}