package com.example.patienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    EditText userEmail;
    Button userpass;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

                userEmail = findViewById(R.id.txtemail);
                userpass = findViewById(R.id.btn_forgot);

                firebaseAuth = FirebaseAuth.getInstance();

                userpass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    Toast.makeText(Forgotpassword.this,"Password Successfully Sent",Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(Forgotpassword.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
            }
        }

