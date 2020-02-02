package com.example.finalapp;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SliderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private TextView[] dots;

    private SliderAdapter sliderAdapter;
    private Button btnN;
    //private FirebaseUser firebaseUser;
    private int CurrentPage = 0;

   /* protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(SliderActivity.this,DoctorProfile.class);
            startActivity(intent);
            finish();
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        viewPager = (ViewPager)findViewById(R.id.vp);
        linearLayout = (LinearLayout)findViewById(R.id.llh);

        btnN = (Button)findViewById(R.id.btnnext);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SliderActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void addDotsIndicator(int pos)
    {
        dots = new TextView[3];
        linearLayout.removeAllViews();

        for(int i=0;i<dots.length;i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorAccent));


            linearLayout.addView(dots[i]);
        }
        if(dots.length > 0)
        {
            dots[pos].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            btnN.setVisibility(View.VISIBLE);
            btnN.setEnabled(true);
            btnN.setText("SKIP >>");
            addDotsIndicator(i);
            CurrentPage = i;



        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
