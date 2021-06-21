package com.technicalrupu.csvtu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.technicalrupu.csvtu.adapter.CourseAdapter;
import com.technicalrupu.csvtu.adapter.SubjectAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.Course;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.Subject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectActivity extends AppCompatActivity {
    RecyclerView SubjectRecyclerview;
    String Resourse;
    ProgressBar progressBar;
    RelativeLayout Progressbar_Layout;
    private  AdView adView;
    FrameLayout adViewContainer;

    @SuppressLint("UseSupportActionBar")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adViewContainer=findViewById(R.id.adViewContainer);
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.Banner_Ad_unit_id));
        adViewContainer.addView(adView);
        loadBanner();


        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Select Subject");
        setActionBar(toolbar);
        progressBar=findViewById(R.id.ProgressBar);
        Progressbar_Layout=findViewById(R.id.progressBar_layout);
     Resourse=getIntent().getStringExtra("RESOURSE");

       SubjectRecyclerview=findViewById(R.id.subjects_recyclerview);
        getSubjects();
    }
    public void getSubjects()
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<ArrayList<Subject>> call=service.getSubjects(cname,SemOrYear);
        call.enqueue(new Callback<ArrayList<Subject>>() {
            @Override
            public void onResponse(Call<ArrayList<Subject>> call, Response<ArrayList<Subject>> response) {
                if(response.body().get(0).getStatus().equals("100")) {
                    Progressbar_Layout.setVisibility(View.GONE);
                    Log.d("rresponse", "onResponse: " + response.body().get(0).getSname());
                    SubjectAdapter subjectAdapter=new SubjectAdapter(Resourse,response.body());
                    SubjectRecyclerview.setAdapter(subjectAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Subject Not  Found",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Subject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
    private void loadBanner() {
        AdRequest adRequest =
                new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
}