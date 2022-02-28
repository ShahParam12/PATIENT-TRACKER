package com.example.patienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp_phone extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String mAuthVerificationId;
    private TextView working;
    private EditText otptxt;
    private Button botp;
    private ProgressBar potp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_phone);

                mAuth=FirebaseAuth.getInstance();
                mUser=mAuth.getCurrentUser();
                mAuthVerificationId=getIntent().getStringExtra("Auth-credentials");
                otptxt=(EditText) findViewById(R.id.editText3);
                botp=(Button) findViewById(R.id.button3);
                potp=(ProgressBar) findViewById(R.id.progressBar2);
                working=(TextView) findViewById(R.id.textView7);
                botp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otp=otptxt.getText().toString();
                        if (otp.isEmpty()){
                            working.setVisibility(View.VISIBLE);
                            working.setText("Please fill the form and continue");
                        } else {
                            potp.setVisibility(View.VISIBLE);
                            botp.setEnabled(false);
                            PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mAuthVerificationId,otp);
                            signInWithPhoneAuthCredential(credential);
                        }

                    }
                });
            }

            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(otp_phone.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sendUserToHome();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        working.setVisibility(View.VISIBLE);
                                        working.setText("There was an error on checking the otp");
                                    }
                                }
                                potp.setVisibility(View.INVISIBLE);
                                botp.setEnabled(true);
                            }
                        });
            }

            protected void onStart() {
                super.onStart();
                if (mUser != null){
                    sendUserToHome();
                }
            }
            public void sendUserToHome() {
                Intent intent=new Intent(otp_phone.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }


