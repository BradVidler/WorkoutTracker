package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_EXERCISE = "exercise";
    static final String KEY_REPS = "reps";
    static final String KEY_WEIGHT = "weight";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyNewDB";
    static final String DATABASE_TABLE = "exercises";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
        "create table exercises (_id integer primary key autoincrement, "
        + "exercise text not null, reps integer not null, weight integer not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS exercises");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }

    //---insert a set into the database---
    public long insertSet(String exercise, int reps, int weight) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EXERCISE, exercise);
        initialValues.put(KEY_REPS, reps);
        initialValues.put(KEY_WEIGHT, weight);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---retrieves all the sets for a given exercise---
    public Cursor getAllSets(String exercise)
    {
    	return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_REPS,
                KEY_WEIGHT}, (KEY_EXERCISE + '=' + "'"+exercise+"' AND " + KEY_REPS + "<> -1"), null, null, null, KEY_WEIGHT);
    }
    
    //get all exercise names in db
    public Cursor getAllExercises()
    {
    	return db.rawQuery("SELECT DISTINCT exercise FROM exercises GROUP BY exercise", null);
    }
}
