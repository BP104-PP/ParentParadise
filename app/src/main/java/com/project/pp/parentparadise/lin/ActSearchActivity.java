package com.project.pp.parentparadise.lin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;

import com.project.pp.parentparadise.R;

public class ActSearchActivity extends AppCompatActivity {

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_search);
        searchView = findViewById(R.id.searchView);
        //searchView.setQueryHint("Search exchange...");

    }
}
