package edu.tacoma.uw.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class NoteMainActivity extends AppCompatActivity {
    Button addNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        addNotes = findViewById(R.id.button);
        addNotes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(NoteMainActivity.this, addNotes);
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
    public void openNote(){
        Intent intent = new Intent(this, NoteDetailActivity.class);
        startActivity(intent);

    }

}