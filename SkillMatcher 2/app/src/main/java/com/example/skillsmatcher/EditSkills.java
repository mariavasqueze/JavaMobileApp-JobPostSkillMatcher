package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillsmatcher.DB.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditSkills extends AppCompatActivity {

    TextView showList;
    EditText skillToDelete;
    Button btnAddSkill, btnDeleteSkill;
    Spinner inputSkill;

    private DBManager dbManager;
    ArrayList<String> skillsArray = new ArrayList<>();
    ArrayList<String> skillsPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skills);

        showList = (TextView) findViewById(R.id.txtSkillsList);
        inputSkill = (Spinner) findViewById(R.id.skillChange);

        skillToDelete = (EditText) findViewById(R.id.skillDelete);
        btnAddSkill = (Button) findViewById(R.id.btnAddNewSkill);
        btnDeleteSkill = (Button) findViewById(R.id.btnDeleteSkill);


        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showList.setMovementMethod(new ScrollingMovementMethod());
        showList.setText("Skills: \n");
        // get skills added
        Cursor cursor = dbManager.fetchSkills();
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                skillsPositions.add(cursor.getString(0));
                skillsArray.add(cursor.getString(1));
                cursor.moveToNext();
            }

            for(int i = 0; i < skillsPositions.size(); i++){
                showList.append(skillsPositions.get(i) + ". " + skillsArray.get(i) + "\n");
                Log.d("", String.join(",",skillsPositions.get(i) + ". " + skillsArray.get(i) + "\n" ));
            }
        }


        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newSkill = inputSkill.getSelectedItem().toString();
                Boolean result = dbManager.checkSkills(newSkill);
                if(result == false){
                    dbManager.insertSkills(newSkill);
                    Toast.makeText(EditSkills.this, "Skill added successfully Please refresh to see on list!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditSkills.this, "This skill already exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = skillToDelete.getText().toString();
                if(s == null || s.equals("")) {
                    Toast.makeText(EditSkills.this, "Please add a number", Toast.LENGTH_SHORT).show();
                } else {
                    boolean numeric = true;
                    numeric = s.matches("-?\\d+(\\.\\d+)?");
                    if(numeric == true){
                        try {
                            long inputSkill = Long.parseLong(skillToDelete.getText().toString());
                            dbManager.deleteSkill(inputSkill);
                            Toast.makeText(EditSkills.this, "Skill deleted!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e){
                            Toast.makeText(EditSkills.this, "There was an error deleting the skill, please try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditSkills.this, "Input is not a number", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}