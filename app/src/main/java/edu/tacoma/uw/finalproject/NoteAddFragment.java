package edu.tacoma.uw.finalproject;
/**
 * This class take all information that user have filled in the UI to save to the table
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.tacoma.uw.finalproject.model.Note;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteAddFragment extends Fragment {

    /**
     * the variable to reference the AddListener interface
     */
    private AddListener mAddListener;

    /**
     * the interface contain the addNote to add the note information to table in
     * NoteDetailActivity class
     */
    public interface AddListener {
        public void addNote(Note note);
    }

    /**
     * access to the username that have been save
     */
    private SharedPreferences mSharedPreferences;
    /**
     * The file stores user information
     */
    public final static String SIGN_IN_FILE_PREFS= "edu.tacoma.uw.finalproject.sign_in_file_prefs";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public NoteAddFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteAddFragment.
     */
    public static NoteAddFragment newInstance(String param1, String param2) {
        NoteAddFragment fragment = new NoteAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    /**
     * cast the interface from fragment to activity to call the addNotes function
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddListener = (AddListener) getActivity();

    }

    /**
     * get all the information the user have enter on the UI and pass to be added to the table
     * by the addNotes methods.Make sure all the information is in correct format before passing it to
     * the add function.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_note_add, container, false);
        getActivity().setTitle("Add new Note");
        mSharedPreferences = this.getActivity().getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);
        final EditText noteWhoEditText = v.findViewById(R.id.note_name);
        final EditText notePhoneEditText = v.findViewById(R.id.note_phone);
        final EditText noteEmailEditText = v.findViewById(R.id.note_email);
        final EditText noteDateEditText = v.findViewById(R.id.note_date);
        noteDateEditText.setText(setCurrentDay(new Date()));
        final EditText noteLocationEditText = v.findViewById(R.id.note_location);
        Button addButton = v.findViewById(R.id.btn_add_note);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String noteID = "";
                String noteWho = noteWhoEditText.getText().toString();
                String username = mSharedPreferences.getString("username",null);
                String notePhone = notePhoneEditText.getText().toString();
                String noteEmail = noteEmailEditText.getText().toString();
                String noteDate = noteDateEditText.getText().toString();
                String noteLocation = noteLocationEditText.getText().toString();
                if (TextUtils.isEmpty(noteEmail) || !noteEmail.contains("@")){
                    Toast.makeText(v.getContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
                    noteEmailEditText.requestFocus();
                }else if(TextUtils.isEmpty(notePhone) || notePhone.length() > 10 ){
                    Toast.makeText(v.getContext(), "Enter valid phone number with 10 digits in format XXXXXXXXXX", Toast.LENGTH_SHORT).show();
                    noteEmailEditText.requestFocus();
                }else{
                    Note note = new Note(noteID, noteWho, username,notePhone, noteEmail, noteDate, noteLocation);
                    if (mAddListener != null){
                        mAddListener.addNote(note);
                    }
                }
            }
        });
        return v;
    }

    /**
     * Take the date and format it to MM/dd/yyyy
     * @param date
     * @return string
     */
    public String setCurrentDay(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}