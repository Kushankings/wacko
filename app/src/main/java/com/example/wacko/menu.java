package com.example.wacko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class menu extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public void goback(View view){
        view.startAnimation(buttonClick);
        Intent i = new Intent(getApplicationContext(),home.class);
        startActivity(i);
        Animatoo.animateSwipeRight(this);
    }

    public  void goabout(View view){
        view.startAnimation(buttonClick);
        Intent i = new Intent(getApplicationContext(),about.class);
        startActivity(i);
        Animatoo.animateDiagonal(this);
    }


    public void gocontact(View view){
        view.startAnimation(buttonClick);
        Intent i = new Intent(getApplicationContext(),contact.class);
        startActivity(i);
        Animatoo.animateDiagonal(this);

    }

    public void gosupport(View view){
        view.startAnimation(buttonClick);
        Intent i = new Intent(getApplicationContext(),supportpage.class);
        startActivity(i);
        Animatoo.animateDiagonal(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
