package com.technicalrupu.csvtu.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.adapter.SubjectAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.Subject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectsFragment extends Fragment {
    RecyclerView SubjectRecyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_subjects, container, false);
        SubjectRecyclerview=view.findViewById(R.id.subjects_recyclerview);

       getSubjects();
        return view;
    }
    public void getSubjects()
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<ArrayList<Subject>> call=service.getSubjects(cname,SemOrYear);
        call.enqueue(new Callback<ArrayList<Subject>>() {
            @Override
            public void onResponse(Call<ArrayList<Subject>> call, Response<ArrayList<Subject>> response) {
                if(response.body().get(0).getStatus().equals("100")) {
                    Log.d("rresponse", "onResponse: " + response.body().get(0).getSname());
                    SubjectAdapter subjectAdapter=new SubjectAdapter("All",response.body());
                    SubjectRecyclerview.setAdapter(subjectAdapter);
                }
                else {
                    Toast.makeText(getContext(),"Subjects Not Found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Subject>> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
            }
        });

    }
}