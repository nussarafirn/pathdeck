package edu.tacoma.uw.finalproject.authenticate;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.tacoma.uw.finalproject.R;

/**
 * This class represents the Login process
 */
public class LoginFragment extends Fragment {



    public interface LoginFragmentListener {
        void login(String email, String pwd, boolean shouldRemember);
        void launchRegisterFragment();
    }
    public LoginFragment() {
    }

    /**
     * Gets the last saved instance
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Shows the login page that validate the user input login credentials.
     * And provide a register option for those who does not have an account.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Sign In");

        final EditText usernameText = view.findViewById(R.id.login_username);
        final EditText pwdText = view.findViewById(R.id.login_password);
        final CheckBox rememberCheckBox = view.findViewById(R.id.remember);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView registerTextView = view.findViewById(R.id.signup_textview);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginFragmentListener) getActivity()).launchRegisterFragment();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String pwd = pwdText.getText().toString();

                // validate username
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(v.getContext(), "Enter a valid username"
                            , Toast.LENGTH_SHORT)
                            .show();
                    usernameText.requestFocus();
                }

                // validate password
                else if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
                    Toast.makeText(v.getContext()
                            , "Enter valid password (at least 6 characters)"
                            , Toast.LENGTH_SHORT)
                            .show();
                    pwdText.requestFocus();
                }

                ((LoginFragmentListener) getActivity()).login(
                        username,
                        pwd,
                        rememberCheckBox.isChecked());

            }
        });
        return view;
    }
}