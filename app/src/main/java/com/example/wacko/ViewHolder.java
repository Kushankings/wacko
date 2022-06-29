package com.example.wacko;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(View itemview){

        super(itemview);

        mView = itemview;

        //item click
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListner.onItemClick(view ,getAdapterPosition());
            }
        });
        //Item long Click

    }

    //Set Details To Recycler View
    public void setDetails(Context ctx ,String title ,String description ,String thumbnail)
    {

        TextView mTitleView = mView.findViewById(R.id.rTitletv);
        TextView mDetailTV = mView.findViewById(R.id.rDescriptiontv);
        //set data to views
        ImageView mImageIv = mView.findViewById(R.id.rImageview);
        mTitleView.setText("  "+title);
        mDetailTV.setText("  "+description);
        Picasso.get().load(thumbnail).into(mImageIv);

    }

    private ViewHolder.ClickListner mClickListner;
 //interface to send Callback.

    public  interface  ClickListner{

        void onItemClick(View view,int position);
        void onItemLongClick(View view , int position);

    }

    public void setOnClickListner(ViewHolder.ClickListner clickListner){
        mClickListner = clickListner;
    }

}
