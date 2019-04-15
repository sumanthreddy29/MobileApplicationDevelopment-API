package com.gsu.yelpsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class YelpAdapter extends RecyclerView.Adapter<YelpAdapter.MyViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private ArrayList<YelpModel> dataModelArrayList;

    public YelpAdapter(Context ctx, ArrayList<YelpModel> dataModelArrayList){
           this.context=ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }
    @NonNull
    @Override
    public YelpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.card_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(YelpAdapter.MyViewHolder holder, int position) {


        if(dataModelArrayList.get(position).getImageURL() != null){

            GlideApp.with(context).load(dataModelArrayList.get(position).getImageURL()).into(holder.iv);

            holder.name.setText(dataModelArrayList.get(position).getName());
            holder.rating.setText(dataModelArrayList.get(position).getRating());
            holder.phone.setText(dataModelArrayList.get(position).getPhone());
            holder.address.setText(dataModelArrayList.get(position).getAddress());
        }
        else{
            holder.iv.setImageDrawable(null);
        }






    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, rating,phone,address;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            rating = (TextView) itemView.findViewById(R.id.rating);
            phone = (TextView) itemView.findViewById(R.id.phone);
            address = (TextView) itemView.findViewById(R.id.address);

            iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }
}