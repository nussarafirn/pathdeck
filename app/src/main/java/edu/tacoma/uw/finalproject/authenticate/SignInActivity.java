package edu.tacoma.uw.finalproject.authenticate;

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

import edu.tacoma.uw.finalproject.MainActivity;
import edu.tacoma.uw.finalproject.MainMenuActivity;
import edu.tacoma.uw.finalproject.R;
import edu.tacoma.uw.finalproject.model.User;

public class SignInActivity extends AppCompatActivity
        implements LoginFragment.LoginFragmentListener {

    private SharedPreferences mSharedPreferences;
    private JSONObject mUserJSON;
    private boolean mLoginMode;
    private String mUsername;
    private boolean mRemember;
//    public final static String SIGN_IN_FILE_PREFS = "edu.uw.tacoma.menakaapp.sign_in_file_prefs";
    public final static String USERNAME = "username";
    public final static String REMEMBER = "remember";


    // NOTE: may need boolean to check if logged in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        // TODO: what is in LOGIN_PREF?
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);

        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sign_in_fragment_id, new LoginFragment())
                    .commit();
        } else {
            Intent i = new Intent(this, MainMenuActivity.class);
            startActivity(i);
            finish();
        }
    }
//
//    @Override
//    public void launchRegisterFragment() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.sign_in_fragment_container, new RegisterFragment())
//                .addToBackStack(null)
//                .commit();
//    }

    @Override
    public void login(String username, String pwd, boolean shouldRemember) {

        // NOTE: - create the json object for passing with the Logon url
        // execute Async Task with login url
        // the following should happen in OnPostExcecute
        // once the login is authen

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


        // set login boolean  =true;


//        mSharedPreferences
//                .edit()
//                .putBoolean(getString(R.string.LOGGEDIN), true)
//                .commit();
//
//        Intent i = new Intent(this, SignInActivity.class);
//        Toast.makeText(getApplicationContext(), "launch"
//                , Toast.LENGTH_SHORT)
//                .show();
//
//        startActivity(i);
//        finish();
    }


    /**
     * from register
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

                    if (mLoginMode) {
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
                                            MainActivity.class);
                    startActivity(i);
                    finish();

                }
//                else {
//                    getSupportFragmentManager().popBackStackImmediate();
//                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on registering a new user"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(String.valueOf(this), e.getMessage());
            }
        }
    }

}


