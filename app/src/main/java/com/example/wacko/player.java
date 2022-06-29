package com.example.wacko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class player extends AppCompatActivity {

    ImageView pImageView;

    VideoView videoView;
    ListView listView;
    String titlename;
    String xyz;
    ArrayList<String> arrayListTrackName =  new ArrayList<>();
    ArrayList<String> arrayListUrl = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        listView = findViewById(R.id.playerlistview);
        pImageView =findViewById(R.id.player_thumbnail);
        jcPlayerView =findViewById(R.id.jcplayer);

         titlename = getIntent().getStringExtra("title");

        videoView = findViewById(R.id.playlistvideo);
        //String path = "android.resource://com.example.wacko/"+R.raw.slimevideo;
       // Uri u = Uri.parse(path);
       // videoView.setVideoURI(u);
      //  videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
         xyz ="playlist/"+titlename.trim();
        takeTracks();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jcPlayerView.playAudio(jcAudios.get(position));
                jcPlayerView.createNotification();

            }
        });

                //FETCH DATA

        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes , 0 ,bytes.length);

        //Set Data to Views
        pImageView.setImageBitmap(bmp);


    }

    private void takeTracks() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(xyz);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    Tracks tracksObj = ds.getValue(Tracks.class);
                    arrayListTrackName.add(tracksObj.getTname());
                    arrayListUrl.add(tracksObj.getTurl());
                    jcAudios.add(JcAudio.createFromURL(tracksObj.getTname(),tracksObj.getTurl()));
                }

                arrayAdapter = new ArrayAdapter<String>(player.this,android.R.layout.simple_list_item_1,arrayListTrackName);
                jcPlayerView.initPlaylist(jcAudios,null);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

