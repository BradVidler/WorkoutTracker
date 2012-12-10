package com.example.workouttracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.workouttracker.DBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddExerciseActivity extends Activity {

	 EditText txtNewExercise;
	 DBAdapter db;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newexercise);
        
        db = new DBAdapter(this);
        txtNewExercise = (EditText) findViewById(R.id.txtNewExercise); 
        try {
            String destPath = "/data/data/" + getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                CopyDB(getBaseContext().getAssets().open("mydb"),
                    new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void CopyDB(InputStream inputStream, 
    	    OutputStream outputStream) throws IOException {
    	        //---copy 1K bytes at a time---
    	        byte[] buffer = new byte[1024];
    	        int length;
    	        while ((length = inputStream.read(buffer)) > 0) {
    	            outputStream.write(buffer, 0, length);
    	        }
    	        inputStream.close();
    	        outputStream.close();
    	    }
    
    public void newExerciseClick(View view)
    {
    	
    	db.open();
        long id = db.insertSet(txtNewExercise.getText().toString(), -1, -1);
        //id = db.insertSet(txtNewExercise.getText().toString(), "-1", "-1");
        db.close();
        Toast.makeText(this,
                "Exercise Added!",
                Toast.LENGTH_LONG).show();
                
    }
    
    public void DisplaySet(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                "Exercise: " + c.getString(1) + "\n" +
                "Reps:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }
}
