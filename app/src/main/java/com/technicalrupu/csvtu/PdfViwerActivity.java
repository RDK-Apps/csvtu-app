package com.technicalrupu.csvtu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class PdfViwerActivity extends AppCompatActivity {
  WebView webView;
    private  AdView adView;
    FrameLayout adViewContainer;
  ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viwer);

        Toolbar toolbar=findViewById(R.id.toolbar);
        progressBar=findViewById(R.id.ProgressBar);
        webView=findViewById(R.id.webview);
        webView.setVisibility(View.GONE);
        toolbar.setTitle(getIntent().getStringExtra("PdfTittle"));
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adViewContainer=findViewById(R.id.adViewContainer);
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.Banner_Ad_unit_id));
        adViewContainer.addView(adView);


        loadPdf();
        loadBanner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.pdf_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.refresh)
         {
             progressBar.setVisibility(View.VISIBLE);
             webView.setVisibility(View.GONE);
             loadPdf();
             Toast.makeText(getApplicationContext(),"Refreshing...",Toast.LENGTH_SHORT).show();

         }
         return true;
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    public void loadPdf()
    {   webView.setWebChromeClient(new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }

        }

    });
    webView.setWebViewClient(new WebViewClient(){
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            loadPdf();
        }
    });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setVisibility(View.VISIBLE);
        String pdf = getIntent().getStringExtra("PdfLink");
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
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