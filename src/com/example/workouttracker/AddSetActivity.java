package com.example.workouttracker;

import java.util.ArrayList;
import java.util.List;

import com.example.workouttracker.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSetActivity extends Activity {

	DBAdapter db;
	Spinner s1;
	EditText reps, weight;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newworkout);
        
        //grab textboxes
        reps = (EditText) findViewById(R.id.txtReps); 
        weight = (EditText) findViewById(R.id.txtWeight); 
        
        //get all exercises and put in spinner
        s1 = (Spinner) findViewById(R.id.spinner1);
        db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllExercises();
        
        // Spinner Drop down elements
        List<String> exerciseList = new ArrayList<String>();
        
        if (c.moveToFirst())
        {
            do {
                exerciseList.add(c.getString(0));
            } while (c.moveToNext());
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, exerciseList);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        s1.setAdapter(dataAdapter);
        
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void addSetClick(View view)
    {
    	db.open();
    	db.insertSet(s1.getSelectedItem().toString(), Integer.parseInt(reps.getText().toString()), Integer.parseInt(weight.getText().toString()));
    	//db.insertSet("'Back Rows'", "10", "30");
    	reps.setText("");
    	weight.setText("");
    	Toast.makeText(this,
                "Set Added!",
                Toast.LENGTH_LONG).show();
    	db.close();
    }
}
