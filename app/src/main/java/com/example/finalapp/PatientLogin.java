package com.example.finalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientLogin extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        email = (EditText)findViewById(R.id.PID);
        password = (EditText)findViewById(R.id.PPW);
        login = (Button)findViewById(R.id.PLI);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id = email.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(email_id)) {
                    Toast.makeText(PatientLogin.this, "Please enter your email id...", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(PatientLogin.this, "Please set a password...", Toast.LENGTH_SHORT).show();
                }
                progressDialog.setMessage("Logging you in...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email_id, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(PatientLogin.this, "Login Error. Please Try Again...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PatientLogin.this, "Redirecting...Please wait....", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PatientLogin.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });

            }
        });


    }
}
