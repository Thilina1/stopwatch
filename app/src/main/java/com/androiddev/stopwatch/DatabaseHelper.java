package com.androiddev.stopwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TimingData.db";
    public static final String TABLE_NAME = "user_timinglist_data";
    public static final String IDcol = "ID";
    public static final String DATAcol = "ITEM1";
    public static final int VERSION = 2;


    /**
     * @param context
     * @author Thilina Weerasinghe
     * need to import sq lite
     * create TimingData.db database here
     * cerate user_timinglist_data table
     * add two columns
     * version is valuble in updating tables
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +  //automatically get id and id is primary key
                " ITEM1 TEXT)";
        db.execSQL(createTable);  //write sql query to create table in database with column names
    }

    /**
     * @param db    database name
     * @param oldVersion  version valuable for update database
     * @param newVersion  version valuable for update database
     * check version for find updates
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);    //this is valuable in upgrade
        onCreate(db);
    }

    /**
     * @param UIData user input data
     * @return true or false
     */
    public boolean addData(String UIData) { //add data to sqlite tables
        SQLiteDatabase db = this.getWritableDatabase();    // passes to db
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATAcol, UIData); //add user input data to columns

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return data return data for display
     * select all from table to display
     * get data
     */
    public Cursor getListContents(){   //get data from sqlite db for display data in listview
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}
