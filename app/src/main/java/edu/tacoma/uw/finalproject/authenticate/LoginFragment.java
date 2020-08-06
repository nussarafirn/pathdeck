package edu.tacoma.uw.finalproject.authenticate;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.tacoma.uw.finalproject.R;
import edu.tacoma.uw.finalproject.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

//    public LoginFragmentListener mLoginFragmentListener;

    public interface LoginFragmentListener {
        void login(String email, String pwd, boolean shouldRemember);
        void launchRegisterFragment();
    }
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mLoginFragmentListener = (LoginFragmentListener) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Sign In");

        Log.i("Login", "here"); //TODO
        final EditText usernameText = view.findViewById(R.id.login_username);
        final EditText pwdText = view.findViewById(R.id.login_password);
        final CheckBox rememberCheckBox = view.findViewById(R.id.remember);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView registerTextView = view.findViewById(R.id.signup_textview);

        Log.i("login", "here2"); // TODO

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

                Log.i("login", "here2.5");
                // validate username
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(v.getContext(), "Enter a valid username"
                            , Toast.LENGTH_SHORT)
                            .show();
                    usernameText.requestFocus();
                    Log.i("login", "here3");
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