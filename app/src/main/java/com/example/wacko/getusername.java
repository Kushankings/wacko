package com.example.wacko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class getusername extends AppCompatActivity {
    FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    EditText username;
    ImageView usernamebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getusername);

        username = (EditText) findViewById(R.id.username);
        usernamebutton = (ImageView) findViewById(R.id.usernamebutton);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        usernamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserName();
            }
        });
        loadUserInforamation();
    }

    public void saveUserName(){
        String userName = username.getText().toString();

        if(userName.isEmpty()){
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }
        



        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();

            firebaseUser.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        finish();
                        Intent i = new Intent(getApplicationContext(),everification.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void loadUserInforamation() {


        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = user.getDisplayName();

        if(displayName != null){
            username.setText(displayName);
        }
       if(displayName == null){
           username.setText("User");
       }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);

        }
    }


}
