package edu.tacoma.uw.finalproject;

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

    private AddListener mAddListener;
    public interface AddListener {
        public void addNote(Note note);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SharedPreferences mSharedPreferences;
    public final static String SIGN_IN_FILE_PREFS= "edu.tacoma.uw.finalproject.sign_in_file_prefs";
    private NoteListActivity passUsername;
    // TODO: Rename and change types of parameters
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
    // TODO: Rename and change types and number of parameters
    public static NoteAddFragment newInstance(String param1, String param2) {
        NoteAddFragment fragment = new NoteAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        mAddListener = (AddListener) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


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

    public String setCurrentDay(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}