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
import com.technicalrupu.csvtu.adapter.ResultAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.Result;
import com.technicalrupu.csvtu.data.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment {
    RecyclerView ResultRecyclerview;
    RelativeLayout progressbar_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        ResultRecyclerview = view.findViewById(R.id.result_recyclerview);
        progressbar_layout=view.findViewById(R.id.progressBar_layout);
        getResults();

        return view;
    }
    public void getResults()
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<ArrayList<Result>> call=service.getResults(cname,SemOrYear);
        call.enqueue(new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                Log.d("rresponse", "onResponse: ");
                if(response.body().get(0).getStatus().equals("100")) {
                    progressbar_layout.setVisibility(View.GONE);
                    ResultAdapter adapter=new ResultAdapter(response.body());
                    ResultRecyclerview.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getContext(),"Results Not found",Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                }
            }
            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
    }
}