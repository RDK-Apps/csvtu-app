package com.technicalrupu.csvtu.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.SubActivity;
import com.technicalrupu.csvtu.data.Course;
import com.technicalrupu.csvtu.data.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.viewHolder>{
     String resourse;
    ArrayList<Subject> list;
    public SubjectAdapter(String resourse, ArrayList<Subject> list) {

        this.resourse=resourse;
        this.list=list;
    }



    @Override
    public viewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_item, parent, false);
        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(SubjectAdapter.viewHolder holder, int position) {
        holder.Subject_Name.setText(list.get(position).getSname());
        Glide.with(holder.itemView.getContext()).load(list.get(position).getLink()).placeholder(R.drawable.logo).into(holder.subject_bg);
        holder.Subject_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), SubActivity.class);
                intent.putExtra("RESOURSE",resourse);
                intent.putExtra("SID",list.get(position).getSid());
                intent.putExtra("SNAME",list.get(position).getSname());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  viewHolder extends RecyclerView.ViewHolder
    {  RelativeLayout Subject_layout;
       ImageView subject_bg;
       TextView Subject_Name;

        public viewHolder(View itemView) {
            super(itemView);
              Subject_layout=itemView.findViewById(R.id.subject_layout);
              subject_bg=itemView.findViewById(R.id.bg);
              Subject_Name=itemView.findViewById(R.id.subject_name);
        }
    }
}
