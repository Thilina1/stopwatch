package com.androiddev.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    /**
     * home page
     * @author  Thilina Weerasinghe | ct2017/068
     * sqlite db
     * btnAdd button for add something to sqlite database
     * btnView for move to another activity
     * EditText for get user inputs
     * editText get user inputs
     * if clicked btnAdd add user input data to SQLite db with move data DatabaseHelper activity
     * if clicked btnView btn open ViewListContents activity
     */

    DatabaseHelper UDB;
    Button btnAdd,btnView;
    EditText editText;
    private int miliSeconds=0;
    private boolean running;
    String time;


    /**
     * @param savedInstanceState
     *
     *
     * call startTimer() method for work stopwatch
     * if clicked btnView button from this activity open ViewListContents activity
     * intent used to perform action on screen
   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        startTimer(); //Call startTimer method to work stop watch

        //Xml connect to java code
        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        UDB = new DatabaseHelper(this);


        /**
         * @param v
         * @param NewEntry user input data
         * if click this button work AddData method work
         * get user input text and set it to string data type
         */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length()!= 0){ // check length of word and if user entered data is greater than 0 send data to AddData method else display message
                    AddData(newEntry);
                    editText.setText("");
                }else{
                    Toast.makeText(HomeActivity.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ViewListContents.class); //if clicked btnView button from this activity open ViewListContents activity
                startActivity(intent);                                                              //intent used to perform action on screen
            }
        });

    }



    /**
     * @param newEntry  user input data via textView
     * add user input data to SQLite database
     * send data to database
     */

    public void AddData(String newEntry){
        boolean insertData= UDB.addData(newEntry);
        if (insertData==true){
            Toast.makeText(HomeActivity.this, "Successfully Entered Data", Toast.LENGTH_LONG).show(); // if successfully enterd data display this message and save data
        }else {
            Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();      // if faild display this message
        }
    }

    /**
     * start true = run the clock
     * stop false = stop the clock
     * reset stop and make milisecond value to 0
     */
    public void start(View view){ //start stop watch
        running=true;
    }
    public void stop(View view){  //stop stopwatch
        running=false;
    }
    public void reset(View view){ //reset stopwatch
        running=false;
        miliSeconds=0;
    }

    /**
     * start timer method
     * in here i added formula for calculate time it works with miliseconds
     * in textview display time
     */
    private void startTimer(){
        TextView timer=findViewById(R.id.timer);                //connect with xml button with id
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int min=miliSeconds/3600;
                int sec=(miliSeconds%3600)/60;
                int mSec = miliSeconds%60;
                time=String.format("%02d:%02d:%02d",min,sec,mSec);  // values set to time
                timer.setText(time);
                if (running){
                    miliSeconds++;                                  //after 1ms increase miliSecond value by +1
                }
                handler.postDelayed(this,1);           //count with miliseconds with time
            }
        });
    }
}