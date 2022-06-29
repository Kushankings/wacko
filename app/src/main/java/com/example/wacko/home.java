package com.example.wacko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;

import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class home extends AppCompatActivity {
    FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    String displayNamae;
    TextView welcomename;
    VideoView videoView;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;


    public void logout(View view)
    {    view.startAnimation(buttonClick);
         FirebaseAuth.getInstance().signOut();
         finish();
        Intent i = new Intent(getApplicationContext(),login.class);

        Animatoo.animateShrink(this);
        startActivity(i);
    }

    public void menuclicked(View view)
    {   view.startAnimation(buttonClick);
        Intent i = new Intent(getApplicationContext(),menu.class);
        startActivity(i);
        Animatoo.animateSwipeLeft(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        welcomename =findViewById(R.id.welcomename);
        if(user != null){
            displayNamae =user.getDisplayName();
        }
        welcomename.setText("Hi ! "+displayNamae);

        //RECYLER VIEW
        mRecyclerView = findViewById(R.id.recylcerview);
        mRecyclerView.setHasFixedSize(true);

        //set layout as linear Layout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //SendQuery to FireBase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("pods");

        videoView = findViewById(R.id.homeVideo);
        String path = "android.resource://com.example.wacko/"+R.raw.dhuadhuavideo;
        Uri u = Uri.parse(path);
        videoView.setVideoURI(u);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }


    //LOAD DATA INTO RECLYCER VIEW ON STARTT


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,
                R.layout.row,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int i) {
                viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getThumbnail());
            }

            @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                    viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                        @Override
                        public void onItemClick(View view, int position) {

                            TextView titlename = view.findViewById(R.id.rTitletv);

                            String mTitle = titlename.getText().toString();

                            ImageView pImageview = view.findViewById(R.id.rImageview);
                            //get data from Views
                            Drawable mDrawable = pImageview.getDrawable();
                            Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                            //pass the data to new activity
                            Intent intent = new Intent(view.getContext(),player.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                            byte[] bytes =stream.toByteArray();
                            intent.putExtra("image",bytes); //put bitmap image as array of bytes
                            intent.putExtra("title",mTitle);
                            startActivity(intent);

                        }


                        @Override
                        public void onItemLongClick(View view, int position) {
                            //null
                        }
                    });

                return viewHolder;

            }
        };

        mLayoutManager = new LinearLayoutManager(home.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        // Now set the layout manager and the adapter to the RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
       //Set adapter to recyclerview
     //   mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
