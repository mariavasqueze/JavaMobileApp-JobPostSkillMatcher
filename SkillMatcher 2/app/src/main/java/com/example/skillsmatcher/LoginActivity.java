package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skillsmatcher.DB.DBManager;


public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnSignin;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.signinUsername);
        password = (EditText) findViewById(R.id.signinPassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);

        dbManager = new DBManager(this);


        btnSignin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserPass = dbManager.checkUsernamePassword(user, pass);
                    if(checkUserPass == true) {
                        Toast.makeText(LoginActivity.this, "You are signed in!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), JobList.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }


            }

        });



    }
}