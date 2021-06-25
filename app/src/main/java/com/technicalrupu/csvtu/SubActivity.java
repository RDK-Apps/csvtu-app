package com.technicalrupu.csvtu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.technicalrupu.csvtu.Fragment.AllResourseFragment;
import com.technicalrupu.csvtu.Fragment.NotesFragment;
import com.technicalrupu.csvtu.Fragment.PYQFragment;
import com.technicalrupu.csvtu.Fragment.ResultFragment;
import com.technicalrupu.csvtu.Fragment.SubjectsFragment;
import com.technicalrupu.csvtu.Fragment.SyllabusFragment;
import com.technicalrupu.csvtu.Fragment.VideoFragment;

public class SubActivity extends AppCompatActivity {

       private  AdView adView;
       FrameLayout adViewContainer;

    @SuppressLint("UseSupportActionBar")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

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

        String Resourse=getIntent().getStringExtra("RESOURSE");
        String Sid=getIntent().getStringExtra("SID");
        String SName=getIntent().getStringExtra("SNAME");
        Fragment fragment=null;
        switch(Resourse)
        {
            case "SYLLABUS":
                toolbar.setTitle("Syllabus");
                fragment=new SyllabusFragment();
                break;
            case "NOTES":
                toolbar.setTitle("Notes");
                 fragment=new NotesFragment(Sid,SName);
                break;
            case "VIDEOS":
                toolbar.setTitle("videos");
                fragment=new VideoFragment(Sid,SName);
                break;
            case "PYQ":
                toolbar.setTitle("PYQ's");
                fragment=new PYQFragment(Sid,SName);
                break;
            case "RESULTS":
                toolbar.setTitle("Results");
                fragment=new ResultFragment();
                break;
            default:
                toolbar.setTitle("All Study Materials");
                fragment=new AllResourseFragment(Sid,SName);
                break;
        }

        setActionBar(toolbar);
        loadfragment(fragment);

    }

    private void loadfragment(Fragment fragmnet) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,fragmnet)
                .commit();
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