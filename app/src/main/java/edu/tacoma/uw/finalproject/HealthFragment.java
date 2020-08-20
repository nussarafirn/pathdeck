package edu.tacoma.uw.finalproject;
/**
 * The page allow the user to keep track of their health and send the notification to other
 * when the covid test is positive
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.tacoma.uw.finalproject.model.Record;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthFragment extends Fragment {

    /**
     * The test card area that show positive or negative
     */
    private CardView card_test;
    /**
     * The list contain all the record that have been enter by user
     */
    private List<Record> mRecordList;

    /**
     * access to the username that have been save
     */
    public SharedPreferences mSharedPreferences;
    /**
     * The file stores user information
     */
    public final String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";
    /**
     * acess the symptom textview from UI
     */
    private TextView symptom;
    /**
     * acess the temperature textview from UI
     */
    private TextView temp;
    /**
     * acess the test result textview from UI
     */
    private TextView testResult;
    /**
     * hold the current login username
     */
    private String username;

    /**
     * Temperature evaluation base on normal body temperature
     */
    private TextView tempState;

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

    /**
     * setup all the variable files
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        card_test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), emailNotifyActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.health_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchHealthAddFragment();
            }
        });
        return view;
    }

    /**
     * call and launch the healthAddFragment class
     */
    public void launchHealthAddFragment(){
        HealthAddFragment healthAddFragment = new HealthAddFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, healthAddFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * passing the urls to recordTask to get the recordList
     */
    @Override
    public void onResume() {
        super.onResume();
        new RecordTask().execute(getString(R.string.get_record));


    }

    private class RecordTask extends AsyncTask<String, Void, String> {

        /**
         * take and read the urls to text string
         * @param urls
         * @return string response
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
                } finally {
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
                Toast.makeText(getActivity(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mRecordList = Record.parseRecJson(
                            jsonObject.getString("records"));
                    if (!mRecordList.isEmpty()) {
                        //setupRecyclerView((RecyclerView) mRecyclerView);
                        temp.setText(getTemp() + " °F");
                        symptom.setText(getSymp());
                        testResult.setText(getTestResult());
                    }
                }
<<<<<<< HEAD


=======
                temp.setText(getTemp() + " °F");
                symptom.setText(getSymp());
                testResult.setText(getTestResult());
                tempState.setText(getTempState());
>>>>>>> aae76148b93d1aa849c77a55e10c56d75b874bab
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * from the recocrd list, get the list of the temperature and
     * return the newest temperature that been enter from the user
     * @return temperature type string
     */
<<<<<<< HEAD
    private String getTemp() {
        String temp = "0";
        List<String> tempList = new ArrayList<>();
=======
    private double getTemp() {
        List<Double> tempList = new ArrayList<>();
>>>>>>> aae76148b93d1aa849c77a55e10c56d75b874bab
        for (Record each : mRecordList) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                tempList.add(each.getTemp());
            }
        }
        if(!tempList.isEmpty()){
            temp = tempList.get(tempList.size()-1);
        }
        return temp;
    }
    /**
     * from the record list, get the list of the symptoms and
     * return the newest symptoms that been enter from the user
     * @return symptom
     */
    private String getSymp() {
        String symp = "none";
        List<String> sympList = new ArrayList<>();
        for (Record each : mRecordList) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                sympList.add(each.getSymp());
            }
        }
        if(!sympList.isEmpty()) {
            symp = sympList.get(sympList.size()-1);
        }
        return symp;
    }
    /**
     * from the record list, get the list of the test result and
     * return the newest test result that been record from the user
     * @return symptom
     */
    private String getTestResult() {
        String Result = "Negative";
        List<String> testRecList = new ArrayList<>();
        for (Record each : mRecordList) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                testRecList.add(each.getRecTest());
            }
        }
        if(!testRecList.isEmpty()) {
            Result = testRecList.get(testRecList.size()-1);
        }
        return Result;
    }

    private String getTempState() {
        double temp = getTemp();
        String s = "";

        if (temp > 99) {
            s = "Dangerous, may have fever!";
        } else {
            s = "Normal";
        }

        return s;
    }
}