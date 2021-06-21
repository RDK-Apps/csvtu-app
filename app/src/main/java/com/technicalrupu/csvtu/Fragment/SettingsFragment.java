package com.technicalrupu.csvtu.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.technicalrupu.csvtu.CourseActivity;
import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.SplashScreen;

public class SettingsFragment extends Fragment {
LinearLayout change_course,Send_FeedBack,Share_App;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        change_course=view.findViewById(R.id.change_course);
        Send_FeedBack=view.findViewById(R.id.Send_Feedback);
        Share_App=view.findViewById(R.id.Share_App);
        change_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CourseActivity.class);
                i.putExtra("status","1");
                startActivity(i);
            }
        });
        Send_FeedBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:plystoreaaps618@gmail.com"));
                    startActivity(Intent.createChooser(intent,"Email Via"));

            }
        });
        Share_App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Get All Courses *Notes*, *Videos*, *PYQ's*, *Result Updates* of CSVTU University\n\n*App Link :* https://play.google.com/store/apps/details?id="+v.getContext().getPackageName());
                   startActivity(Intent.createChooser(intent,"Share Via"));
            }
        });

        return  view;
    }
}