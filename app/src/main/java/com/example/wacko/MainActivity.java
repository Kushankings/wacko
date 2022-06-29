package com.example.wacko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {



    public void next (View view){

        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
        Animatoo.animateSplit(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
