package edu.tacoma.uw.finalproject;
/**
 * This MainMenu will lunch after the user login successfully
 * @author Kieu Trinh
 * @version summer 2020
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import edu.tacoma.uw.finalproject.authenticate.*;

public class MainMenuActivity extends AppCompatActivity {
    //THe button for note popup choices
    public Button addNotes;

    /**
     * call and start the review note
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addNotes = findViewById(R.id.button);
        addNotes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainMenuActivity.this, addNotes);
                popup.getMenuInflater().inflate(R.menu.covidnotemenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.covid_note:
                                openNote();
                                return true;
                        }

                        return false;
                    }
                });
                popup.show();
            }

        });

    }

    /**
     * call the notedetail class to show up the list of the notes
     */
    public void openNote(){
        Intent intent = new Intent(this, NoteDetailActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences sharedPreferences =
                    getSharedPreferences(MainActivity.SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);
            sharedPreferences.edit()
                    .clear()
                    .commit();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}