package edu.tacoma.uw.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.tacoma.uw.finalproject.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private AddListener mSignUpListener;

    public interface AddListener {
        public void signUp(User user);
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpListener = (AddListener) getActivity();
    }

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
        final EditText emailEditText = v.findViewById(R.id.email);
        final EditText phoneEditText = v.findViewById(R.id.phone);
        Button signUpButton = v.findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "im in", Toast.LENGTH_SHORT).show();
                String firstN = firstNEditText.getText().toString();
                String lastN = lastNEditText.getText().toString();
                String userN = userNEditText.getText().toString();
                String pwd = pwdEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                User user = new User(firstN, lastN, userN, pwd, email, phone);
                if (mSignUpListener != null) {
                    mSignUpListener.signUp(user);
                }
            }
        });

        return v;
    }

}