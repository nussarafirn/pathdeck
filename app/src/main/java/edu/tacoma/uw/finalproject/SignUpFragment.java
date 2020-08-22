package edu.tacoma.uw.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.tacoma.uw.finalproject.model.User;

/**
 * This class represents the sign up fragment that register a user based on
 * information that they fill
 *
 * Author: Firn Tieanklin
 * Version: 2.0
 * Date: 08/10/20
 */
public class SignUpFragment extends Fragment {

    /**
     * The Listener for sign up activity
     */
    private AddListener mSignUpListener;

    public interface AddListener {
        public void signUp(User user);
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Instantiate the sign up listener
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpListener = (AddListener) getActivity();
    }

    /**
     * Register a user base on the user's input
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container
                , false);
        getActivity().setTitle("Register");
        final EditText firstNEditText = v.findViewById(R.id.firstname);
        final EditText lastNEditText = v.findViewById(R.id.lastname);
        final EditText userNEditText = v.findViewById(R.id.username);
        final EditText pwdEditText = v.findViewById(R.id.password);
        final EditText confirmPwdEditText = v.findViewById(R.id.confirm_password);
        final EditText emailEditText = v.findViewById(R.id.email);
        final EditText phoneEditText = v.findViewById(R.id.phone);
        Button signUpButton = v.findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstN = firstNEditText.getText().toString();
                String lastN = lastNEditText.getText().toString();
                String userN = userNEditText.getText().toString();
                String pwd = pwdEditText.getText().toString();
                String confirmPwd = confirmPwdEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                User user = new User(firstN, lastN, userN, pwd, email, phone);

                if (TextUtils.isEmpty(firstN)) {        // validate Fisrt Name
                    Toast.makeText(v.getContext(),
                            "Enter valid first name"
                            , Toast.LENGTH_SHORT)
                            .show();
                    firstNEditText.requestFocus();

                } else if (TextUtils.isEmpty(lastN)) {  // validate password
                    Toast.makeText(v.getContext()
                            , "Enter valid lastname"
                            , Toast.LENGTH_SHORT)
                            .show();
                    lastNEditText.requestFocus();

                } else if (TextUtils.isEmpty(userN)) {  // validate password
                    Toast.makeText(v.getContext()
                            , "Enter valid username"
                            , Toast.LENGTH_SHORT)
                            .show();
                    userNEditText.requestFocus();

                } else if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {  // validate password
                    Toast.makeText(v.getContext()
                            , "Enter valid password (at least 6 characters)"
                            , Toast.LENGTH_SHORT)
                            .show();
                    pwdEditText.requestFocus();

                } else if (!pwd.equals(confirmPwd)) {  // validate confirm pwd
                    Toast.makeText(v.getContext()
                            , "Password doesn't match"
                            , Toast.LENGTH_SHORT)
                            .show();
                    confirmPwdEditText.requestFocus();
                } else if (TextUtils.isEmpty(email) || !email.contains("@")) {        // validate email
                        Toast.makeText(v.getContext(), "Enter valid email address"
                                , Toast.LENGTH_SHORT)
                                .show();
                        emailEditText.requestFocus();
                } else if (TextUtils.isEmpty(phone) ||phone.length() != 10) {        // validate phone
                    Toast.makeText(v.getContext(),
                            "Enter valid phone number in format XXXXXXXXXX (10 characters)"
                            , Toast.LENGTH_SHORT)
                            .show();
                        phoneEditText.requestFocus();
                } else if (mSignUpListener != null) {
                    mSignUpListener.signUp(user);
                }
            }
        });

        return v;
    }

}