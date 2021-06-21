package com.technicalrupu.csvtu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technicalrupu.csvtu.adapter.CourseAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.Course;
import com.technicalrupu.csvtu.data.Notes;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.pdf;
import com.technicalrupu.csvtu.data.semester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends AppCompatActivity {
     ExpandableListView expandableListView;
     ArrayList<String> listgroup=new ArrayList<>();
     HashMap<String,ArrayList<String>> listchild=new HashMap<>();

     CourseAdapter adapter;
    RelativeLayout progressbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        expandableListView=findViewById(R.id.course_expandablelistview);
        progressbar_layout=findViewById(R.id.progressBar_layout);
    getCourse();
    }
   public void getCourse()
    {
        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        Call <Course> call=service.getCourses();
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if(response.body().getStatus().equals("100")) {
                    ArrayList<Course> list=new ArrayList<>();
                    ArrayList<String> cname=response.body().getCname();
                    ArrayList<semester> sem=response.body().getSemoryear();
                    for(int i=0;i<cname.size();i++)
                    {
                        ArrayList<semester> templist=new ArrayList<>();
                        for(int j=0;j<sem.size();j++)
                        {
                            if(cname.get(i).equals(sem.get(j).getCname()))
                            {
                                templist.add(sem.get(j));
                            }
                        }

                        list.add(new Course(cname.get(i),templist));

                    }
                    progressbar_layout.setVisibility(View.GONE);
                    adapter = new CourseAdapter(list,getIntent().getStringExtra("status"));
                    expandableListView.setAdapter( adapter);
                }
                else {
                    Toast.makeText(getApplicationContext(),"no Course Found",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
               Toast.makeText(getApplicationContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
               finish();
            }
        });

    }
}