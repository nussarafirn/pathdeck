package edu.tacoma.uw.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

/**
 * TODO: Currently a placeholder for Main Menu showing calendar
 */
public class CalendarFragment extends Fragment {
    //The button for note popup choices
    public Button addNotes;


    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //mSharedPreferences = getSharedPreferences("edu.tacoma.uw.finalproject.sign_in_file_prefs", Context.MODE_PRIVATE);
//
//        addNotes = findViewById(R.id.button);
//        addNotes.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                PopupMenu popup = new PopupMenu(MainMenuActivity.this, addNotes);
//                popup.getMenuInflater().inflate(R.menu.covidnotemenu, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        switch (menuItem.getItemId()){
//                            case R.id.covid_note:
//                                openNote();
//                                //Toast.makeText(MainMenuActivity.this,"Hello"+ mSharedPreferences.getString("username",null), Toast.LENGTH_SHORT).show();
//                                return true;
//                        }
//
//                        return false;
//                    }
//                });
//                popup.show();
//            }
//
//        });
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}