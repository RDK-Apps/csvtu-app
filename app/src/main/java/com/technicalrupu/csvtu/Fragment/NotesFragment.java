package com.technicalrupu.csvtu.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.adapter.PdfResourseAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.Notes;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesFragment extends Fragment {
    ExpandableListView expandableListView;
    PdfResourseAdapter adapter;
      String Sid;
      String Sname;
    RelativeLayout progressbar_layout;
    public NotesFragment(String sid, String sName) {
        this.Sid=sid;
        this.Sname=sName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resourse, container, false);
        expandableListView=view.findViewById(R.id.course_expandablelistview);
        progressbar_layout=view.findViewById(R.id.progressBar_layout);
       getNotes(Sid,Sname);
        return view;

    }
    public void getNotes(String sid, String sName)
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<Notes> call=service.getNotes(sName,cname,SemOrYear);
        call.enqueue(new Callback<Notes>() {
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
                    progressbar_layout.setVisibility(View.GONE);
                   adapter=new PdfResourseAdapter(list,sName);
                   expandableListView.setAdapter(adapter);

                }
                else {
                    Toast.makeText(getContext(),"Notes Not Available",Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                }
            }
            @Override
            public void onFailure(Call<Notes> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

    }
}