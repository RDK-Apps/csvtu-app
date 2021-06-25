package com.technicalrupu.csvtu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.technicalrupu.csvtu.adapter.VideoResourseAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.Notes;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.Screenshot;
import com.technicalrupu.csvtu.data.pdf;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayerActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    PlayerView playerView;
    ProgressBar progressBar,progressBar2;
    ImageView btFullScreen;
    ExoPlayer simpleExoPlayer;
    VideoResourseAdapter adapter;
    ExpandableListView expandableListView;
    TextView tittle,subjectName;
    RelativeLayout p1;
    LinearLayout btn_like,btn_dislike,btn_share,btn_save,p2,Video_Player_View;
    boolean flag=false;
    private AdView adView;
    FrameLayout adViewContainer;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Video_Player_View=findViewById(R.id.video_player_view);
        playerView=findViewById(R.id.player_view);
        progressBar=findViewById(R.id.progress_bar);
        progressBar2=findViewById(R.id.ProgressBar2);
        btFullScreen=findViewById(R.id.bt_fullscreen);
        tittle=findViewById(R.id.video_tittle);
        tittle.setText(getIntent().getStringExtra("VideoTittle"));
        subjectName=findViewById(R.id.subject_name);
        btn_like=findViewById(R.id.btn_like);
        btn_dislike=findViewById(R.id.btn_dislike);
        btn_share=findViewById(R.id.btn_share);
        btn_save=findViewById(R.id.btn_save);
        p1=findViewById(R.id.p1);
        Video_Player_View.setMinimumHeight(300);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Liked",Toast.LENGTH_SHORT).show();
            }
        });

        btn_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Disliked",Toast.LENGTH_SHORT).show();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Activity activity = (Activity) v.getContext();
                LinearLayout Video_view=findViewById(R.id.video_player_view);
                View VideoImage=Video_view.getRootView();
                Bitmap bitmap = Screenshot.getInstance().takeScreenshotForView(Video_view);
                String bitmapPath= MediaStore.Images.Media.insertImage(v.getContext().getContentResolver(),bitmap,"tittle","");
                Uri uri=Uri.parse(bitmapPath);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.putExtra(Intent.EXTRA_TEXT,"*"+tittle.getText()+"*\n*Subject Name :* "+
                        subjectName.getText()+"\n\nGet All Courses *Notes*, *Videos*, *PYQ's*, *Result Updates* of CSVTU University\n\n*App Link :* https://play.google.com/store/apps/details?id="+v.getContext().getPackageName());
                activity.startActivity(Intent.createChooser(intent,"Share via"));
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Saving Feature is coming Soon in future Updates",Toast.LENGTH_SHORT).show();
            }
        });
        subjectName.setText(getIntent().getStringExtra("SubjectName"));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Uri  videoUrl=Uri.parse(getIntent().getStringExtra("videoUrl"));

        LoadControl loadControl=new DefaultLoadControl();

        BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();


        TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(VideoPlayerActivity.this,trackSelector,loadControl);

        DefaultHttpDataSourceFactory factory=new DefaultHttpDataSourceFactory("exeplayer_video");

        ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(videoUrl,factory,extractorsFactory,null,null);

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);

        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                   if(playbackState==Player.STATE_BUFFERING)
                   {
                       progressBar.setVisibility(View.VISIBLE);
                   }else if(playbackState==Player.STATE_READY){
                       progressBar.setVisibility(View.GONE);
                   }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    p2=findViewById(R.id.p2);

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if(flag){
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen1));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag=false;
                }else {
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag=true;

                }
            }
        });

       String sName=getIntent().getStringExtra("SNAME");
       expandableListView=findViewById(R.id.course_expandablelistview);
        getVideos(sName);
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


    @Override
    protected void onPause() {
        super.onPause();

        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();

    }
    public void getVideos(String sName)
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<Notes> call=service.getVideos(sName,cname,SemOrYear);
        call.enqueue(new Callback<Notes>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Notes> call, Response<Notes> response) {
                if(response.body().getStatus().equals("100")) {
                    ArrayList<Notes> list=new ArrayList<Notes>();
                    ArrayList<String> unit=response.body().getUnit();
                    ArrayList<pdf> pdf=response.body().getSublist();
                    for(int i=0;i<unit.size();i++)
                    {
                        ArrayList<pdf> templist=new ArrayList<>();
                        for(int j=0;j<pdf.size();j++)
                        {
                            if(unit.get(i).equals(pdf.get(j).getUnitTittle()))
                            {
                                templist.add(pdf.get(j));
                            }
                        }

                        list.add(new Notes(unit.get(i),templist));

                    }
                    progressBar2.setVisibility(View.GONE);
                    adapter=new VideoResourseAdapter(list,sName);
                    expandableListView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Videos Not Found...",Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
            @Override
            public void onFailure(Call<Notes> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd != null) {
            Log.d("mads", "onBackPressed: "+"not loaded");
            mInterstitialAd.show(VideoPlayerActivity.this);
            finish();
        } else {
            finish();
        }
    }
}