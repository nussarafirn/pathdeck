package edu.tacoma.uw.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.finalproject.model.User;

/**
 * This class will sign up a user base on the given information
 *
 * Author: Firn Tieanklin
 * Version: 2.0
 * Date: 08/10/20
 */
public class RegistrationActivity extends AppCompatActivity
            implements SignUpFragment.AddListener {

    /**
     * The JSON object to get all the regiser
     */
    private JSONObject mUserJSON;

    /**
     * The string sign up
     */
    public static final String SIGN_UP = "SIGN_UP";

    /**
     * Launches sign up fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // launch fragment
        getSupportFragmentManager().beginTransaction()
        .add(R.id.sign_up_fragment_container, new SignUpFragment())
        .commit();
    }

    /**
     * Created JSON object that preparing data for registration p
     * @param user the user to  register
     */
    @Override
    public void signUp(User user) {
        StringBuilder url = new StringBuilder(getString(R.string.register_user));

        //Construct a JSONObj to build a formatted message to send
        mUserJSON = new JSONObject();
        try {
            mUserJSON.put(User.FIRST_NAME, user.getFirstName());
            mUserJSON.put(User.LAST_NAME, user.getLastName());
            mUserJSON.put(User.USER_NAME, user.getUserName());
            mUserJSON.put(User.PASS_WORD, user.getPassword());
            mUserJSON.put(User.EMAIL, user.getEmail());
            mUserJSON.put(User.PHONE, user.getPhone());
            new AddUserAsyncTask().execute(url.toString());
        } catch (JSONException e) {
                Toast.makeText(this, "Error with JSON creation on adding a user" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This AsyncTask will sign up the user by connecting to the database
     */
    private class AddUserAsyncTask extends AsyncTask<String, Void, String> {

        /**
         * Connects to the backend to prepare the registration process
         *
         * @param urls the url to connnect to POST method of the database
         * @return error if was not successful
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
                    OutputStream outputStream = urlConnection.getOutputStream();
                    OutputStreamWriter wr =
                            new OutputStreamWriter(outputStream);



                    // For Debugging
                    Log.i(SIGN_UP, mUserJSON.toString());
                    wr.write(mUserJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to sign up a new user, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * Registering a new user with given input personal information
         *
          * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to add the new user")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "RegistrationActivity successful"
                            , Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "User couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(SIGN_UP, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on registering a new user"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(SIGN_UP, e.getMessage());
            }
    }
    }
}