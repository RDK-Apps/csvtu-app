package com.technicalrupu.csvtu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.technicalrupu.csvtu.Fragment.ELibraryFragment;
import com.technicalrupu.csvtu.Fragment.HomeFragment;
import com.technicalrupu.csvtu.Fragment.SettingsFragment;
import com.technicalrupu.csvtu.Fragment.SubjectsFragment;
import com.technicalrupu.csvtu.Fragment.SyllabusFragment;

public class HomeActivity extends AppCompatActivity {
private  InterstitialAd mInterstitialAd;
    @SuppressLint("UseSupportActionBar")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setActionBar(toolbar);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_syllabus));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_elibrary));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_settings));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment=null;
                switch (item.getId())
                {
                    case 1:
                        toolbar.setTitle("Home");
                        fragment=new HomeFragment(HomeActivity.this);
                        break;
                    case 2:
                        toolbar.setTitle("Syllabus");
                        fragment=new SyllabusFragment();
                        break;
                    case 3:
                        toolbar.setTitle("ELibrary");
                        fragment=new ELibraryFragment();
                        break;
                    case 4:
                        toolbar.setTitle("Settings");
                        fragment=new SettingsFragment();
                        break;
                    default:
                        toolbar.setTitle("Home");
                        fragment=new HomeFragment(HomeActivity.this);
                        break;
                }
                layoutmanager(fragment);
            }
        });
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.show(1,true);
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.Interstitial_Ad_unit_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });
    }

    private void layoutmanager(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd != null) {
            Log.d("mads", "onBackPressed: "+"not loaded");
            mInterstitialAd.show(HomeActivity.this);
            finish();
        } else {
            finish();
        }
    }
}