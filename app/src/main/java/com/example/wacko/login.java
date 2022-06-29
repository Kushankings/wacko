package com.example.wacko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText Lemail;
    EditText Lpassword;
  FirebaseAuth mAuth;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    public void jumptoreg (View view){
        view.startAnimation(buttonClick);
        Intent i = new Intent(getApplicationContext(),register.class);
        startActivity(i);
        Animatoo.animateSwipeLeft(this);

    }

    public void jumptomain (View view){
        view.startAnimation(buttonClick);
        userLogin();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Lemail = findViewById(R.id.email);
        Lpassword =findViewById(R.id.password);


    }

    public void userLogin(){
        String email = Lemail.getText().toString().trim();
        String password = Lpassword.getText().toString().trim();


        if(email.isEmpty()){
            Lemail.setError("Email Required");
            Lemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Lemail.setError("Enter a valid email");
            Lemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            Lpassword.setError("Password Required");
            Lpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(task.isSuccessful()){
                    if(user.isEmailVerified()){
                        finish();
                        Intent i = new Intent(getApplicationContext(),home.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }else{
                        finish();
                        Intent i = new Intent(getApplicationContext(),getusername.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }


                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            Intent i = new Intent(getApplicationContext(),home.class);
            startActivity(i);
        }
    }


}
