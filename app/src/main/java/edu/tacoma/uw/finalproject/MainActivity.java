package edu.tacoma.uw.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.finalproject.authenticate.LoginFragment;
import edu.tacoma.uw.finalproject.model.User;

/**
 * This class is the main class that contains the login.
 * It get launched if the user has not yet sign in.
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener {

    /**
     * The share preferences file that keeps track username and whether the user wants to remember
     * the login information.
     */
    private SharedPreferences mSharedPreferences;

    /**
     * JSON object to pass in login credentials
     */
    private JSONObject mUserJSON;

    /**
     * Tells whether this device has already been logged in
     */
    private boolean mLoginMode;

    /**
     * The username on the account
     */
    public String mUsername;

    /**
     * Tells whether the user wants to remember the login information.
     */
    private boolean mRemember;

    /**
     * The file stores user information
     */
    public final static String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";

    /**
     * The username
     */
    public final static String USERNAME = "username";

    /**
     * The remember me option
     */
    public final static String REMEMBER = "remember";


    /**
     * Launches the MainMenu page if the account credential has been previously remembered.
     * Otherwise, this will launch the sign in page requesting the user input
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // always launch to the activity main
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);

        // if there is username saved
        if (mSharedPreferences.getString(USERNAME, null) != null) {
            Intent i = new Intent(getApplicationContext(),
                    MainMenuActivity.class);
            startActivity(i);
            finish();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.sign_in_fragment_container, new LoginFragment())
                    .commit();
        }

    }

    /**
     * Create the json object for passing with the Login base on given url
     * execute Async Task with login
     *
     * @param username username for log in
     * @param pwd password for log in
     * @param shouldRemember true if the user has previously check to remember the credentials,
     *                       otherwise false
     */
    @Override
    public void login(String username, String pwd, boolean shouldRemember) {

        // NOTE: - create the json object for passing with the Logon url
        // execute Async Task with login url


        StringBuilder url = new StringBuilder(getString(R.string.login_user));
        mLoginMode = true;
        mUsername = username;
        mRemember = shouldRemember;

        mUserJSON = new JSONObject();
        try {
            mUserJSON.put(User.USER_NAME, mUsername);
            mUserJSON.put(User.PASS_WORD, pwd);
            new AuthenticateAsyncTask().execute(url.toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Error with JSON creation on logging in a user" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Launches the Register Fragment
     */
    @Override
    public void launchRegisterFragment() {

        Intent i = new Intent(getApplicationContext(),
                RegistrationActivity.class);
        startActivity(i);
    }


    /**
     * This class will connect to the url to process the login
     * and store credentials if the user would like to
     */
    private class AuthenticateAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    Log.i("AuthenticateAsyncTask", "authen try");

                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStream outputStream = urlConnection.getOutputStream();
                    OutputStreamWriter wr =
                            new OutputStreamWriter(outputStream);



                    // For Debugging
                    Log.i("Sign In", mUserJSON.toString());
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
                    response = "Unable to log in a new user, Reason: "
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
            if (s.startsWith("Unable to add the new user")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Log in successful"
                            , Toast.LENGTH_SHORT).show();

                    if (mLoginMode && mRemember) {
                        mSharedPreferences.edit()
                                .putString(USERNAME, mUsername)
                                .putBoolean(REMEMBER, mRemember)
                                .commit();
                    } else {
                        mSharedPreferences.edit()
                                .clear()
                                .commit();
                    }

                    Intent i = new Intent(getApplicationContext(),
                            MainMenuActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Username or password was incorrect, please register before sign in if you haven't done so."
                            , Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on registering a new user"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(String.valueOf(this), e.getMessage());
            }
        }
    }

}