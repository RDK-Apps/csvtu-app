package com.technicalrupu.csvtu.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.SubActivity;
import com.technicalrupu.csvtu.SubjectActivity;

public class AllResourseFragment extends Fragment {
LinearLayout syllabus_btn,Notes_btn,Videos_btn,Pyq_btn;
    String Sid;
    String Sname;
    RelativeLayout progressbar_layout;
    public AllResourseFragment(String sid, String sName) {
        this.Sid=sid;
        this.Sname=sName;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_resourse, container, false);
        Notes_btn=view.findViewById(R.id.notes_btn);
        Videos_btn=view.findViewById(R.id.videos_btn);
        progressbar_layout=view.findViewById(R.id.progressBar_layout);
        progressbar_layout.setVisibility(View.GONE);
        Pyq_btn=view.findViewById(R.id.pyq_btn);
        Notes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SubActivity.class);
                intent.putExtra("RESOURSE","NOTES");
                intent.putExtra("SID",Sid);
                intent.putExtra("SNAME",Sname);
                startActivity(intent);
            }
        });
        Videos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SubActivity.class);
                intent.putExtra("RESOURSE","VIDEOS");
                intent.putExtra("SID",Sid);
                intent.putExtra("SNAME",Sname);
                startActivity(intent);
            }
        });
        Pyq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SubActivity.class);
                intent.putExtra("RESOURSE","PYQ");
                intent.putExtra("SID",Sid);
                intent.putExtra("SNAME",Sname);
                startActivity(intent);
            }
        });
        return view;
    }
}