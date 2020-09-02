package com.example.recycleview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity_Adapter extends RecyclerView.Adapter<MainActivity_Adapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> mData;
    public Context mContext;
    LayoutInflater layoutInflater;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public TextView item_subject;

        public ViewHolder(View v) {
            super(v);
            item_name = (TextView) v.findViewById(R.id.item_name);
            item_subject = (TextView) v.findViewById(R.id.item_name);

        }
    }

    public MainActivity_Adapter(Context mContext, ArrayList<HashMap<String, String>> mData) {
        this.mData = mData;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MainActivity_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater
                .inflate(R.layout.mainactivity_item, parent, false);
        MainActivity_Adapter.ViewHolder vh = new MainActivity_Adapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MainActivity_Adapter.ViewHolder holder, final int position) {

        holder.item_name.setText(mData.get(position).get("name"));
        holder.item_subject.setText(mData.get(position).get("subject"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click","click");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
