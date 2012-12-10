package com.example.workouttracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void newExerciseClick(View view) {
    	startActivity(new Intent("com.example.AddExerciseActivity"));
    	}
    
    public void newSetClick(View view) {
    	startActivity(new Intent("com.example.AddSetActivity"));
    	}
    
    public void historyClick(View view) {
    	startActivity(new Intent("com.example.HistoryActivity"));
    }
    
    public void newAboutClick(View view) {
    	startActivity(new Intent("com.example.AboutActivity"));
    }
}
