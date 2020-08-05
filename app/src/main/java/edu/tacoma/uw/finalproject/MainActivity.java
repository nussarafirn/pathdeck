package edu.tacoma.uw.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.tacoma.uw.finalproject.authenticate.LoginFragment;
import edu.tacoma.uw.finalproject.authenticate.SignInActivity;

public class MainActivity extends AppCompatActivity {
    private TextView Signup;
    private Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        Signup = findViewById(R.id.signup_button);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

        LoginBtn = findViewById(R.id.loginButton);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.sign_in_fragment_id, new LoginFragment())
                        .commit();
                openCalendar();
            }
        });
    }

    public void openRegister() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Launching calendar page after user sign in successfully
     */
    public void openCalendar() {

        //      TODO  replace test with calendar page
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.sign_in_fragment_id, new LoginFragment())
//                .commit();

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

}