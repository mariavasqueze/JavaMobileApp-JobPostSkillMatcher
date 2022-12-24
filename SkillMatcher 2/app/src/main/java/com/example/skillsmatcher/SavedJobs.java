package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillsmatcher.DB.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class SavedJobs extends AppCompatActivity {

    private DBManager dbManager;
    TextView matchesList;
    EditText inputId;
    Button btnDeleteMatch;

    ArrayList<String> matchesIds = new ArrayList<>();
    ArrayList<String> matchesTitle = new ArrayList<>();
    ArrayList<String> matchesDesc = new ArrayList<>();
    ArrayList<String> matchesSaved = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);

        matchesList = (TextView) findViewById(R.id.txtMatchesList);
        inputId = (EditText) findViewById(R.id.inputDelete);
        btnDeleteMatch = (Button) findViewById(R.id.btnDeleteMatch);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        matchesList.setMovementMethod(new ScrollingMovementMethod());
        matchesList.setText("All Saved Matches: \n");
        // get matches added
        Cursor cursor = dbManager.fetchJobMatch();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                matchesIds.add(cursor.getString(0));
                matchesTitle.add(cursor.getString(1));
                matchesDesc.add(cursor.getString(2));
                matchesSaved.add(cursor.getString(3));
                cursor.moveToNext();
            }

            for(int i = 0; i < matchesIds.size(); i++){
                matchesList.append(matchesIds.get(i) + ". " + matchesTitle.get(i) + " -> "+ matchesSaved.get(i) + "\n");
                Log.d("", matchesIds.get(i) + ". " + matchesTitle.get(i) + matchesSaved.get(i) + "\n");
            }
        }


        btnDeleteMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = inputId.getText().toString();
                if(s == null || s.equals("")) {
                    Toast.makeText(SavedJobs.this, "Please add a number", Toast.LENGTH_SHORT).show();
                } else {
                    boolean numeric = true;
                    numeric = s.matches("-?\\d+(\\.\\d+)?");
                    if(numeric == true){
                        try {
                            long inputMatch = Long.parseLong(inputId.getText().toString());
                            dbManager.deleteJobMatch(inputMatch);
                            Toast.makeText(SavedJobs.this, "Match deleted!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e){
                            Toast.makeText(SavedJobs.this, "There was an error deleting the match, please try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SavedJobs.this, "Input is not a number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}