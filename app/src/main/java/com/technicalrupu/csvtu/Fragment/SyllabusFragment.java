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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.adapter.SyllabusResourseAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.Syllabus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SyllabusFragment extends Fragment {
   RecyclerView recyclerView;
   SyllabusResourseAdapter adapter;
    RelativeLayout progressbar_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_result, container, false);
        recyclerView=view.findViewById(R.id.result_recyclerview);
        progressbar_layout=view.findViewById(R.id.progressBar_layout);

getSyllabus(view);
        return  view;
    }
    public void getSyllabus(View view)
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<ArrayList<Syllabus>> call=service.getSyllabus(cname,SemOrYear);
        call.enqueue(new Callback<ArrayList<Syllabus>>() {
            @Override
            public void onResponse(Call<ArrayList<Syllabus>> call, Response<ArrayList<Syllabus>> response) {
                Log.d("rresponse", "onResponse: ");
                if(response.body().get(0).getStatus().equals("100")) {
                    progressbar_layout.setVisibility(View.GONE);
                    adapter=new SyllabusResourseAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                }
                else {
                    progressbar_layout.setVisibility(View.GONE);
                    Toast.makeText(view.getContext(),"Syllabus Not Found",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Syllabus>> call, Throwable t) {
                progressbar_layout.setVisibility(View.GONE);
                Toast.makeText(view.getContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
            }
        });

    }
}