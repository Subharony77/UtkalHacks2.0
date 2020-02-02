package com.example.finalapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private Button sign_up;
    private Button login;

    private RadioGroup radioGroup;
    private RadioButton radioButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup)findViewById(R.id.radio_g);
        sign_up = (Button)findViewById(R.id.sign_up);
        login = (Button)findViewById(R.id.login);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rid = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(rid);
                if(rid==R.id.doctor_r)
                {
                    Intent i = new Intent(MainActivity.this,DoctorSignUp.class);
                    startActivity(i);
                }
                else //if(rid==R.id.patient_r)
                {
                    Intent i = new Intent(MainActivity.this,PatientSignUp.class);
                    startActivity(i);
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rid = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(rid);
                if(rid==R.id.doctor_r)
                {
                    Intent i = new Intent(MainActivity.this,DoctorLogin.class);
                    startActivity(i);
                }
                else if(rid==R.id.patient_r)
                {
                    Intent i = new Intent(MainActivity.this,PatientLogin.class);
                    startActivity(i);
                }

            }
        });
    }
}
