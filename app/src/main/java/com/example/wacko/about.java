package com.example.wacko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class about extends AppCompatActivity {

    public void arrowabout(View view){
        finish();
        Intent i = new Intent(getApplicationContext(),menu.class);
        startActivity(i);
        Animatoo.animateDiagonal(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
