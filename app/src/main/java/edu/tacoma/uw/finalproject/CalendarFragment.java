package edu.tacoma.uw.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Currently a placeholder for Main Menu showing calendar
 */
public class CalendarFragment extends Fragment {
    //The button for note popup choices
    private Button addNotes;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        addNotes = view.findViewById(R.id.button);

        addNotes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(getActivity(), addNotes);
                popup.getMenuInflater().inflate(R.menu.covidnotemenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.covid_note:
                                openNote();
                                return true;
                            case R.id.memo_note:
                                //Toast.makeText(getActivity(), "list" + call.emailList, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), emailNotifyActivity.class);
                                startActivity(intent);
                                return true;

                        }
                        return false;
                    }
                });
                popup.show();
            }

        });
    return view;
    }



    public void openNote(){
        Intent intent = new Intent(getActivity(), NoteListActivity.class);
        startActivity(intent);

    }
}