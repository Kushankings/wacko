package com.example.wacko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class everification extends AppCompatActivity {
    FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    TextView text;
    String displayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everification);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        text = (TextView) findViewById(R.id.everify_Text);
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        displayName = user.getDisplayName();
        puttingText();

    }

    public void checked(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        if(user.isEmailVerified()){
            Intent i = new Intent(getApplicationContext(),home.class);
            startActivity(i);
        }
        else{
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        finish();
                        Intent i = new Intent(getApplicationContext(),done.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void puttingText(){

        text.setText("Hello " + displayName + " ,You are almost there ,we just need to verify your email for better connection");

    }
}
