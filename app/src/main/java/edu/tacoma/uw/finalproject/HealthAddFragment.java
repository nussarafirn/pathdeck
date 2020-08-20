package edu.tacoma.uw.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.tacoma.uw.finalproject.model.Note;
import edu.tacoma.uw.finalproject.model.Record;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthAddFragment extends Fragment {

    private HealthAddListener healthAddListener;
    private SharedPreferences mSharedPreferences;
    public final static String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";
//    private NoteListActivity passUsername;

    public interface HealthAddListener {
        public void addHealth(Record record);
    }

    public HealthAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HealthAddFragment.
     */
    public static HealthAddFragment newInstance(String param1, String param2) {
        HealthAddFragment fragment = new HealthAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthAddListener = (HealthAddListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_health_add, container, false);
        getActivity().setTitle("Update health information");
        mSharedPreferences = this.getActivity().getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);

        final EditText filledTemp = v.findViewById(R.id.health_add_temp);
        final EditText filledSymp = v.findViewById(R.id.health_add_symp);
        final EditText filledTest = v.findViewById(R.id.health_add_test);
        final EditText filledDate = v.findViewById(R.id.health_add_date);
        Button updateButton = v.findViewById(R.id.btn_update_health);

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String recID = "";
                double temp =  Double.parseDouble(filledTemp.getText().toString());
                String username = mSharedPreferences.getString("username",null);
                String symp = filledSymp.getText().toString();
                String rec_test = filledTest.getText().toString();
                String rec_date = filledDate.getText().toString();

//                if (temp < 90.0 || temp > 130.0){
//                    Toast.makeText(v.getContext(), "Enter valid body temperature (97-99 °F)",
//                            Toast.LENGTH_SHORT).show();
//                    filledTemp.requestFocus();
//                }else
                    if(TextUtils.isEmpty(symp)){
                    Toast.makeText(v.getContext(), "Enter valid symptom, enter N/A if not applicable",
                            Toast.LENGTH_SHORT).show();
                    filledSymp.requestFocus();
                }else if(TextUtils.isEmpty(rec_test)
                        ){
                    Toast.makeText(v.getContext(), "Enter valid testing result (i.e. Positive, Negative, or N/A)",
                            Toast.LENGTH_SHORT).show();
                    filledTest.requestFocus();

//                    || !rec_test.equalsIgnoreCase("Positive")
//                            || !rec_test.equalsIgnoreCase("Negative")
//                            || !rec_test.equalsIgnoreCase("N/A")
                }else{

                        Log.i("HealthAddF", "HEEEEERRRRRRRRRRRE2");
                    Record record = new Record(recID, username, temp, symp,rec_test, rec_date);
                        Log.i("HealthAddF", "HEEEEERRRRRRRRRRRE2.5");
                    if (healthAddListener != null){
                        Log.i("HealthAddF", "HEEEEERRRRRRRRRRRE3");
                        healthAddListener.addHealth(record);

                    }
                }
            }
        });
        return v;
    }


}