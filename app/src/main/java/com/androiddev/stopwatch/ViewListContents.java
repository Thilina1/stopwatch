package com.androiddev.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Display user entered data in this page
 * @author Thilina Weerasinghe | ct2017/068
 * this activity is for view data
 * with clicking bBtn open the HomeActivity
 * get data and display in ListView
 */
public class ViewListContents extends AppCompatActivity {

    Button bBtn; //back button
    DatabaseHelper UDB;  //database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        bBtn= findViewById(R.id.button2);  //connect with xml button with id

        bBtn.setOnClickListener(new View.OnClickListener() { //with clicking back button open the HomeActivity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewListContents.this, HomeActivity.class);
                startActivity(i);
            }
        });


        ListView listView = (ListView) findViewById(R.id.listView);
        UDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = UDB.getListContents();
        if(data.getCount() == 0){                                                          //check data is empty
            Toast.makeText(this, "No saved data!",Toast.LENGTH_LONG).show(); //if empty display this message
        }else{
            while(data.moveToNext()){   // display data
                theList.add(data.getString(1));                                //get data and display data
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }


    }
}