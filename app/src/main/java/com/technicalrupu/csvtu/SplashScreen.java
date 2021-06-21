   package com.technicalrupu.csvtu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

   public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);


                String cid = sh.getString("cid", " ");
                String SemOrYear = sh.getString("SemOrYear"," ");
                if(cid.equals(" ") && SemOrYear.equals(" "))
                {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplashScreen.this, CourseActivity.class);
                    i.putExtra("status","0");
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, 5000);
    }
}