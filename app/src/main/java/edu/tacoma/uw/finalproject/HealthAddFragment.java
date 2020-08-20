package edu.tacoma.uw.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.tacoma.uw.finalproject.model.Note;
import edu.tacoma.uw.finalproject.model.Record;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthAddFragment extends Fragment {

    public static final String ADD_REC = "ADD_REC";
    private JSONObject recJSON;
    private HealthAddListener healthAddListener;
    private SharedPreferences mSharedPreferences;
    public final static String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";
//    private NoteListActivity passUsername;

    public interface HealthAddListener {
        public void addHealth(Record record);
    }

    public HealthAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HealthAddFragment.
     */
    public static HealthAddFragment newInstance(String param1, String param2) {
        HealthAddFragment fragment = new HealthAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        healthAddListener = (HealthAddListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_health_add, container, false);
        getActivity().setTitle("Update health information");
        mSharedPreferences = this.getActivity().getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);

        final EditText filledTemp = v.findViewById(R.id.health_add_temp);
        final EditText filledSymp = v.findViewById(R.id.health_add_symp);
        final EditText filledTest = v.findViewById(R.id.health_add_test);
        final EditText filledDate = v.findViewById(R.id.health_add_date);
        Button updateButton = v.findViewById(R.id.btn_update_health);

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String recID = "";
                double temp =  Double.parseDouble(filledTemp.getText().toString());
                String username = mSharedPreferences.getString("username",null);
                String symp = filledSymp.getText().toString();
                String rec_test = filledTest.getText().toString();
                String rec_date = filledDate.getText().toString();

                if (temp < 90.0 || temp > 130.0){
                    Toast.makeText(v.getContext(), "Enter valid body temperature (97-99 °F)",
                            Toast.LENGTH_SHORT).show();
                    filledTemp.requestFocus();
                }else
                    if(TextUtils.isEmpty(symp)){
                    Toast.makeText(v.getContext(), "Enter valid symptom, enter N/A if not applicable",
                            Toast.LENGTH_SHORT).show();
                    filledSymp.requestFocus();
                }else if(TextUtils.isEmpty(rec_test)
                         || (!rec_test.equalsIgnoreCase("Positive")
                            && !rec_test.equalsIgnoreCase("Negative")
                            && !rec_test.equalsIgnoreCase("N/A")))
                        {
                    Toast.makeText(v.getContext(), "Enter valid testing result (i.e. Positive, Negative, or N/A)",
                            Toast.LENGTH_SHORT).show();
                    filledTest.requestFocus();
                }else{

                    Record record = new Record(recID, username, temp, symp,rec_test, rec_date);

                        StringBuilder url = new StringBuilder(getString(R.string.add_record));
                        recJSON = new JSONObject();

                        try {
                            recJSON.put(Record.REC_USERNAME, record.getUsername());
                            recJSON.put(Record.REC_TEMP, record.getTemp());
                            recJSON.put(Record.REC_SYMP, record.getSymp());
                            recJSON.put(Record.REC_TEST, record.getRecTest());
                            recJSON.put(Record.REC_DATE, record.getRecDate());
                            new RecordAddAsyncTask().execute(url.toString());

                        } catch (JSONException e) {

                            Toast.makeText(getContext(), "Error with JSON creation on update health record"
                                            + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                }
            }
        });
        return v;
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
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getContext(), "Health record updated successfully"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Health record couldn't be updated: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_REC, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "JSON Parsing error on updating health record"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(ADD_REC, e.getMessage());
            }
        }

    }

}