package com.example.wacko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class register extends AppCompatActivity implements View.OnClickListener{

 EditText Remail;
 EditText Rpassword;
 EditText RconfirmPassword;
 ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Remail = (EditText) findViewById(R.id.Remail);
        Rpassword = (EditText) findViewById(R.id.Rpassword);
        RconfirmPassword = (EditText) findViewById(R.id.Rconfirmpassword);
        progressBar = (ProgressBar) findViewById(R.id.pg);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.regButton).setOnClickListener(this);
    }

    public void jumptolog (View view)
    {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
        Animatoo.animateSwipeRight(this);
    }

    public  void registerUser(){


        String email = Remail.getText().toString().trim();
        String password = Rpassword.getText().toString().trim();
        String confirmPassword = RconfirmPassword.getText().toString().trim();

        if(email.isEmpty()){
            Remail.setError("Email Required");
            Remail.requestFocus();
                    return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Remail.setError("Enter a valid email");
            Remail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            Rpassword.setError("Password Required");
            Rpassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            Rpassword.setError("more than 6 character Required");
            Rpassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            RconfirmPassword.setError("Confirm Password Required");
            RconfirmPassword.requestFocus();
            return;
        }
        if(password.equals(confirmPassword)){

            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        finish();
                        Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),login.class);
                        startActivity(i);
                    }else
                    {
                       if(task.getException() instanceof FirebaseAuthUserCollisionException){
                           Toast.makeText(getApplicationContext(),"Email already used",Toast.LENGTH_SHORT).show();
                       }else
                       {
                           Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       }
                    }
                }
            });
        }else{
            RconfirmPassword.setError("Confirm Password Not Matched");
            RconfirmPassword.requestFocus();
            return;
        }


        }





    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.regButton:
                 registerUser();

        }
    }
}
