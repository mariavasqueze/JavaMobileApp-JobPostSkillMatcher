package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillsmatcher.DB.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class MatchResult extends AppCompatActivity {

    TextView resultTitle, resultDesc;
    Button btnBackToMain, saveJobPost;

    private DBManager dbManager;

    ArrayList<String> skillsArray = new ArrayList<>();
    String desc, title, skills;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);

        btnBackToMain = (Button) findViewById(R.id.btnBackToList);
        saveJobPost = (Button) findViewById(R.id.btnSaveJob);
        resultTitle = (TextView) findViewById(R.id.txtResultTitle);
        resultDesc = (TextView) findViewById(R.id.txtResultDescription);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // get from job post
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description").toLowerCase();


        // get from skills added
        Cursor cursor = dbManager.fetchSkills();

        if((cursor != null) && (cursor.getCount() > 0)){
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                    skillsArray.add(cursor.getString(1).toLowerCase());
                    cursor.moveToNext();

            }
            for(String r : skillsArray){
                // to call the compare method for each skill in skills
                Boolean res = checkForMatch(r, desc);
                if(res == true){
                    count++;
                }

                skills += r;
                Log.d("", skills);
            }



        }




        resultTitle.setText("For the: " + title + " position:");
        String result = "You have " + count + " skills that match.";
        resultDesc.setText(result);


        // save job post in DB
        saveJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.insertJobMatch(title, desc, result);
                Toast.makeText(MatchResult.this, "Result added successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // back to main job list
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchResult.this, JobList.class);
                startActivity(intent);
            }
        });
    }

    // method to compare the description with the skill
    public Boolean checkForMatch(String skill, String jobDesc){
        Boolean result = false;
        if(jobDesc.contains(skill)){
            result = true;
        }
        return result;
    } 
}