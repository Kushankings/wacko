package com.example.wacko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class done extends AppCompatActivity {
    FirebaseAuth mAuth;


    public void doneclicked(View view){
        FirebaseUser user = mAuth.getCurrentUser();
       if(user != null){
        if(user.isEmailVerified()){
                finish();
            Intent i = new Intent(getApplicationContext(),home.class);
            startActivity(i);
        }
        else{
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);
        }
       }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        mAuth = FirebaseAuth.getInstance();
    }
}
