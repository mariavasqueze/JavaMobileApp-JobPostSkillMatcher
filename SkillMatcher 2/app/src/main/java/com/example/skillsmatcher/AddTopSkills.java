package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.skillsmatcher.DB.DBManager;

import java.sql.SQLException;

public class AddTopSkills extends AppCompatActivity {

    Button saveAndGoToListings;
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_top_skills);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveAndGoToListings = (Button) findViewById(R.id.btnSeeJobPosts);

        spinner1 = (Spinner) findViewById(R.id.skill1);
        spinner2 = (Spinner) findViewById(R.id.skill2);
        spinner3 = (Spinner) findViewById(R.id.skill3);
        spinner4 = (Spinner) findViewById(R.id.skill4);
        spinner5 = (Spinner) findViewById(R.id.skill5);

        saveAndGoToListings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

//                 Add inputs to database
                if(checkSkill(spinner1) == false){
                    addData(spinner1);
                }

                if(checkSkill(spinner2) == false){
                    addData(spinner2);
                }

                if(checkSkill(spinner3) == false){
                    addData(spinner3);
                }

                if(checkSkill(spinner4) == false){
                    addData(spinner4);
                }

                if(checkSkill(spinner5) == false){
                    addData(spinner5);
                }

                    Intent intent = new Intent(AddTopSkills.this, JobList.class);
                    startActivity(intent);

            }

        });

    }
    public void addData(Spinner sk) {
        String skillData = sk.getSelectedItem().toString();
        dbManager.insertSkills(skillData);
    }

    public Boolean checkSkill(Spinner sk){
        Boolean result;
        String skillData = sk.getSelectedItem().toString();
        result = dbManager.checkSkills(skillData);
        return result;
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("Application","onDestroy");
        dbManager.close();
    }


}