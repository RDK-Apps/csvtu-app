package com.technicalrupu.csvtu.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.technicalrupu.csvtu.HomeActivity;
import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.SubActivity;
import com.technicalrupu.csvtu.SubjectActivity;
import com.technicalrupu.csvtu.adapter.SubjectAdapter;
import com.technicalrupu.csvtu.data.Api;
import com.technicalrupu.csvtu.data.RetrofitInstance;
import com.technicalrupu.csvtu.data.Slider;
import com.technicalrupu.csvtu.data.Subject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView SubjectRecyclerview;
    ImageSlider imageSlider;
    Button About_csvtu;
    HashMap<String, String> HashMapForURL ;
    Activity homeActivity;
   public HomeFragment(Activity activity)
   {
       homeActivity=activity;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);


         imageSlider=view.findViewById(R.id.image_slider);
         getSlider(view);
          getSubjects(view);

           SubjectRecyclerview=view.findViewById(R.id.subjects_recyclerview);
//            SubjectAdapter subjectAdapter=new SubjectAdapter("All", list);
//            SubjectRecyclerview.setAdapter(subjectAdapter);

        Button btn_notes=view.findViewById(R.id.btn_notes);
        Button btn_videos=view.findViewById(R.id.btn_video);
        Button btn_pyq=view.findViewById(R.id.btn_pyqs);
        Button btn_results=view.findViewById(R.id.btn_Results);
        btn_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), SubjectActivity.class);
                intent.putExtra("RESOURSE","NOTES");
                startActivity(intent);
            }
        });
        btn_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), SubjectActivity.class);
                intent.putExtra("RESOURSE","VIDEOS");
                startActivity(intent);
            }
        });
        btn_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), SubjectActivity.class);
                intent.putExtra("RESOURSE","PYQ");
                startActivity(intent);
            }
        });
        btn_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), SubActivity.class);
                intent.putExtra("RESOURSE","RESULTS");
                startActivity(intent);
            }
        });
        About_csvtu= view.findViewById(R.id.about_csvtu);
        About_csvtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://csvtu.ac.in");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            }
        });
        return view;

    }
    public void getSubjects(View view)
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
                    Toast.makeText(view.getContext(),"Subjects Not Found",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Subject>> call, Throwable t) {
                Toast.makeText(view.getContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getSlider(View view)
    {


        RetrofitInstance retrofitInstance=new RetrofitInstance();
        Api service=retrofitInstance.getServices();
        SharedPreferences sh = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String cname = sh.getString("cname", " ");
        String SemOrYear = sh.getString("SemOrYear"," ");
        Call<ArrayList<Slider>> call=service.getSlider(cname,SemOrYear);
        call.enqueue(new Callback<ArrayList<Slider>>() {
            ArrayList<SlideModel> imageList= new ArrayList<>();
            @Override
            public void onResponse(Call<ArrayList<Slider>> call, Response<ArrayList<Slider>> response) {
                Log.d("rresponse", "onResponse: ");
                if(response.body().get(0).getStatus().equals("100")) {

                    for (int i=0;i<response.body().size();i++)
                    {
                        imageList.add(new SlideModel(response.body().get(i).getImglink(),ScaleTypes.FIT));
                    }
                    imageSlider.startSliding(2000);
                    imageSlider.setImageList(imageList,ScaleTypes.FIT);
                }
                else {
                    imageList.add(new SlideModel("https://csvtu01.herokuapp.com/Sliders/s1.jpg", ScaleTypes.FIT));
                    imageList.add(new SlideModel("https://csvtu01.herokuapp.com/Sliders/s2.jfif",ScaleTypes.FIT));
                    imageList.add(new SlideModel("https://csvtu01.herokuapp.com/Sliders/s3.jpg",ScaleTypes.FIT));
                    imageSlider.startSliding(2000);
                    imageSlider.setImageList(imageList,ScaleTypes.FIT);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<Slider>> call, Throwable t) {
                imageList.add(new SlideModel("https://csvtu01.herokuapp.com/Sliders/s1.jpg", ScaleTypes.FIT));
                imageList.add(new SlideModel("https://csvtu01.herokuapp.com/Sliders/s2.jfif",ScaleTypes.FIT));
                imageList.add(new SlideModel("https://csvtu01.herokuapp.com/Sliders/s3.jpg",ScaleTypes.FIT));
                imageSlider.startSliding(2000);
                imageSlider.setImageList(imageList,ScaleTypes.FIT);
            }
        });
    }
}