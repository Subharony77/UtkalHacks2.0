package com.example.finalapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class DoctorProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText Name, Age, Sex, Symptoms, Diagnosis, Prescription, Advice, Buffer;
    private String temp;
    public int status;
    private TextView tv;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private FirebaseAuth mAuth;
    private Button GPDF;
    long millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        Name = (EditText) findViewById(R.id.name);
        Age = (EditText) findViewById(R.id.age);
        Sex = (EditText) findViewById(R.id.sex);
        Symptoms = (EditText) findViewById(R.id.symptoms);
        Diagnosis = (EditText) findViewById(R.id.diagnosis);
        Prescription = (EditText) findViewById(R.id.prescription);
        Advice = (EditText) findViewById(R.id.advice);
        Buffer = (EditText) findViewById(R.id.buffer);
        linearLayout1 = (LinearLayout)findViewById(R.id.ll);
        linearLayout2 = (LinearLayout)findViewById(R.id.ll1);
        tv = (TextView)findViewById(R.id.dpb);
        GPDF = (Button)findViewById(R.id.GPDF);



        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        GPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf(("Reference Number: " + Long.toString(millis)), ("Name: " + Name.getText().toString()), ("Age: " + Age.getText().toString()), ("Gender: " + Sex.getText().toString()), ("Symptoms: " + Symptoms.getText().toString()), ("Diagnosis: " + Diagnosis.getText().toString()), ("Prescription: " + Prescription.getText().toString()), ("Advice: " + Advice.getText().toString()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.save){
            millis = new Date().getTime();
            temp = Buffer.getText().toString();
            tv.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.VISIBLE);
            Name.setText(temp.substring((temp.lastIndexOf("name") + 5),(temp.indexOf("age") - 1)));
            Age.setText(temp.substring((temp.lastIndexOf("age") + 4), (temp.indexOf("gender") - 1)));
            Sex.setText(temp.substring((temp.lastIndexOf("gender") + 7), (temp.indexOf("symptoms") - 1)));
            Symptoms.setText(temp.substring((temp.lastIndexOf("symptoms") + 9), (temp.indexOf("diagnosis") - 1)));
            Diagnosis.setText(temp.substring((temp.lastIndexOf("diagnosis") + 10), (temp.indexOf("prescription") - 1)));
            Prescription.setText(temp.substring((temp.lastIndexOf("prescription") + 13), (temp.indexOf("advice") - 1)));
            Advice.setText(temp.substring(temp.lastIndexOf("advice") + 7));
            /*String last = "Name: " + Name.getText().toString() + "|" + "Age: " + Age.getText().toString() +  "|";
            last = last + "Gender: " + Sex.getText().toString() + "|" + "Symptoms: " + Symptoms.getText().toString() + "|";
            last = last + "Diagnosis: " + Diagnosis.getText().toString() + "|" + "Prescription: " + Prescription.getText().toString();
            last = last + "|" + "Advice: " + Advice.getText().toString();*/


        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new) {
            linearLayout1.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            Buffer.setText(" ");
        } else if (id == R.id.nav_open) {

        } else if (id == R.id.nav_pl) {

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            startActivity(new Intent(DoctorProfile.this,MainActivity.class));
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createPdf(String s0,String s1,String s2, String s3, String s4, String s5, String s6, String s7){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        //paint.setColor(Color.RED);
        //canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        /*int x,y,pointer = 0;
        for(y=20; y<590 ;y=y+10)
        {
            for(x=20; x<280; x = x+10)
            {
                if(sometext.substring(pointer, (pointer + 1)).compareTo("|") == 1)
                {
                    y = y+10;
                    x = 20;
                }

                pointer++;
                if(pointer >= sometext.length()) break;
            }
        }*/

        //canvas.drawt
        // finish the page
        canvas.drawText(s0, 20, 20, paint);
        canvas.drawText(s1, 20, 50, paint);
        canvas.drawText(s2, 20, 80, paint);
        canvas.drawText(s3, 20, 110, paint);
        canvas.drawText(s4, 20, 140, paint);
        canvas.drawText(s5, 20, 170, paint);
        canvas.drawText(s6, 20, 200, paint);
        canvas.drawText(s7, 20, 230, paint);
        document.finishPage(page);
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"prescription.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something went wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
}
