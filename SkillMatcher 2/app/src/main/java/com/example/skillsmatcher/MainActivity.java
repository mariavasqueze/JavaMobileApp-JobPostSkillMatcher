package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skillsmatcher.DB.DBManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText username, password, rePassword, pickDate;
    Button signin, signup;
    DatePickerDialog datePickerDialog;

    DBManager dbManager;
    String birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // logo image
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        username = (EditText) findViewById(R.id.signupUsername);
        password = (EditText) findViewById(R.id.signupPassword);
        rePassword = (EditText) findViewById(R.id.signupRepassword);
        pickDate = (EditText) findViewById(R.id.pickDate);

        signin = (Button) findViewById(R.id.btnGoToSignin);
        signup = (Button) findViewById(R.id.btnSignup);



        dbManager = new DBManager(this);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        pickDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        birthDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();

                // check if any of the fields is empty
                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(MainActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(repass)){
                        Boolean checkUser = dbManager.checkUsername(user);

                        //if user does not exist, insert data in db:
                        if(checkUser == false){
                                dbManager.insert(user, pass, birthDate);
                                Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), AddTopSkills.class);
                                startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists, please login!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }

        });

    }

    protected void onDestroy(){
        super.onDestroy();
        dbManager.close();
    }
}