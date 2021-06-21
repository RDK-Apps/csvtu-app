package com.technicalrupu.csvtu.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technicalrupu.csvtu.HomeActivity;
import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.data.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends BaseExpandableListAdapter {
        ArrayList<Course> list;
    String value;
    ImageView arrow;
    public CourseAdapter(ArrayList<Course> list, String value) {
        this.list=list;
        this.value=value;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSemoryear().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition).getStatus();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSemoryear().get(childPosition).getSemoryear();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item,parent,false);
        TextView textView=view.findViewById(R.id.textview1);
        textView.setText(list.get(groupPosition).getStatus());
        ImageView logo=view.findViewById(R.id.logo);
         arrow=view.findViewById(R.id.arrow);
        logo.setImageDrawable(view.getResources().getDrawable(R.drawable.logo));
        return view;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item,parent,false);
        if(list.get(groupPosition).getSemoryear().size()>0)
        {
            arrow.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_down));
        }
        else{
            arrow.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_right));
        }
        TextView textView=view.findViewById(R.id.textview1);
        textView.setText(list.get(groupPosition).getSemoryear().get(childPosition).getSemoryear());
        LinearLayout ChildLayout=view.findViewById(R.id.child_layout);
        ChildLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("cname", ""+list.get(groupPosition).getStatus());
                myEdit.putString("SemOrYear", list.get(groupPosition).getSemoryear().get(childPosition).getSemoryear());
                myEdit.apply();
                if(value.equals("1"))
                {
                    Toast.makeText(view.getContext(),"Course changed Successfully....",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(),"Course Registered Successfully....",Toast.LENGTH_SHORT).show();
                }

                Intent intent=new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
