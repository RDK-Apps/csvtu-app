package com.technicalrupu.csvtu.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technicalrupu.csvtu.R;
import com.technicalrupu.csvtu.data.Result;
import com.technicalrupu.csvtu.data.Screenshot;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.viewHolder> {
   ArrayList<Result> list;
    public ResultAdapter(ArrayList<Result> list) {
        this.list=list;
    }

    @Override
    public ResultAdapter.viewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder( ResultAdapter.viewHolder holder, int position) {
          holder.tittle.setText(list.get(position).getTittle());
          holder.issuedon.setText(list.get(position).getIssuedon());
          holder.otherinfo.setText(list.get(position).getOtherinfo());
          holder.share.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Activity activity = (Activity) v.getContext();
                  Bitmap bitmap = Screenshot.getInstance().takeScreenshotForView(holder.fullview);
                  String bitmapPath= MediaStore.Images.Media.insertImage(v.getContext().getContentResolver(),bitmap,"tittle","");
                  Uri uri=Uri.parse(bitmapPath);
                  Intent intent=new Intent(Intent.ACTION_SEND);
                  intent.setType("image/png");
                  intent.putExtra(Intent.EXTRA_STREAM,uri);
                  intent.putExtra(Intent.EXTRA_TEXT,"*"+holder.tittle.getText()+"*\n*Issued On:* "+
                          holder.issuedon.getText()+"\n*Other Information :* "+holder.otherinfo.getText()+"\n\nGet All Courses *Notes*, *Videos*, *PYQ's*, *Result Updates* of CSVTU University\n\n*App Link :* https://play.google.com/store/apps/details?id="+v.getContext().getPackageName());
                  activity.startActivity(Intent.createChooser(intent,"Share via"));

              }
          });
        holder.viewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.issuedon.getContext(),list.get(position).getResultlink(),Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(list.get(position).getResultlink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
      TextView tittle,otherinfo,issuedon;
      Button share,viewResult;
      View fullview;
        public viewHolder(View itemView) {
            super(itemView);
            tittle=itemView.findViewById(R.id.tittle);
            issuedon=itemView.findViewById(R.id.issuedon);
            otherinfo=itemView.findViewById(R.id.otherinfo);
            share=itemView.findViewById(R.id.BtnShare);
            viewResult=itemView.findViewById(R.id.BtnViewResult);
            fullview=itemView;

        }
    }
}
