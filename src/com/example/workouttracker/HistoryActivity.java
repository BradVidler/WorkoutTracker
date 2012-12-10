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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HistoryActivity extends Activity {

	DBAdapter db;
	Spinner s1;
	ListView lv1;

	SimpleCursorAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        //grab listview
        lv1 = (ListView) findViewById(R.id.listView1);
        
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
        
        s1.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
        	//@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        	{
        		//display all sets for this exercise
        		List<String> sets = new ArrayList<String>();
        		db.open();
        		Cursor c = db.getAllSets(s1.getSelectedItem().toString());
                
        		// The desired columns to be bound
        		  String[] columns = new String[] {
        		    db.KEY_REPS,
        		    db.KEY_WEIGHT
        		  };
        		  
        		// the XML defined views which the data will be bound to
        		  int[] to = new int[] { 
        		    R.id.reps,
        		    R.id.weight,
        		  };
         
                // Creating adapter for listvew
                adapter = new SimpleCursorAdapter(
                		HistoryActivity.this,
                		R.layout.set_layout, 
                		c,
                        columns,
                        to);
                
                lv1.setAdapter(adapter);
        
        		db.close();
        	}
        	
        	//@Override
        	public void onNothingSelected(AdapterView<?> arg0){ } 
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
