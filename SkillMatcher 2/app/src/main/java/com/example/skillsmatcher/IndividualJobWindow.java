package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IndividualJobWindow extends AppCompatActivity {

    TextView txtTitle, txtDesc;
    Button btnCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_job_window);

        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");

        txtTitle = (TextView) findViewById(R.id.txtIndvTitle);
        txtDesc = (TextView) findViewById(R.id.txtIndvDesc);
        btnCompare = (Button) findViewById(R.id.btnCompareSkills);

        txtDesc.setMovementMethod(new ScrollingMovementMethod());

        txtTitle.setText(title);
        txtDesc.setText(desc);


        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndividualJobWindow.this, MatchResult.class);

                // from the job post:
                Bundle bundle1 = new Bundle();

                bundle1.putString("title", title);
                bundle1.putString("description", desc);

                intent.putExtras(bundle1);

                startActivity(intent);
            }
        });

    }
}