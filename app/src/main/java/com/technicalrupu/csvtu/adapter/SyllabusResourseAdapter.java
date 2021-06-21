package com.technicalrupu.csvtu.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technicalrupu.csvtu.PdfViwerActivity;
import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.data.Syllabus;

import java.util.ArrayList;

public class SyllabusResourseAdapter extends RecyclerView.Adapter<SyllabusResourseAdapter.ViewHolder> {
     ArrayList<Syllabus> list;

    public SyllabusResourseAdapter(ArrayList<Syllabus> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public SyllabusResourseAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
         View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  SyllabusResourseAdapter.ViewHolder holder, int position) {
               holder.SubjectName.setText(list.get(position).getSubjectName());
        Glide.with(holder.itemView.getContext()).load(list.get(position).getBglink()).placeholder(R.drawable.logo).into(holder.bg);
holder.group_layout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(v.getContext(), PdfViwerActivity.class);
        intent.putExtra("PdfTittle",list.get(position).getSubjectName()+"  Syllabus");
        intent.putExtra("PdfLink",list.get(position).getSyllabusLink());
        v.getContext().startActivity(intent);
    }
});

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView SubjectName;
        LinearLayout group_layout;
        ImageView bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SubjectName=itemView.findViewById(R.id.textview1);
            bg=itemView.findViewById(R.id.logo);
            group_layout=itemView.findViewById(R.id.group_layout);

        }
    }
}
