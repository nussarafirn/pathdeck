package edu.tacoma.uw.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.tacoma.uw.finalproject.model.Record;

public class RecordAddActivity extends AppCompatActivity implements HealthAddFragment.HealthAddListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add);
    }

    @Override
    public void addHealth(Record record) {

    }
}