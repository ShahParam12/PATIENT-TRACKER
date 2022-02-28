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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class login_phone extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mcc;
    private EditText mpn;
    private Button bt;
    private ProgressBar mpg;
    private TextView working;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

                mAuth=FirebaseAuth.getInstance();
                mUser=mAuth.getCurrentUser();
                mcc=(EditText) findViewById(R.id.editText);
                mpn=(EditText) findViewById(R.id.editText2);
                bt=(Button) findViewById(R.id.button2);
                mpg=(ProgressBar) findViewById(R.id.progressBar);
                working=(TextView) findViewById(R.id.textView6);

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cc=mcc.getText().toString();
                        String pn=mpn.getText().toString();
                        String cc1="+" + cc  +  pn;
                        if (cc.isEmpty() || pn.isEmpty()){
                            working.setText("Verification failed please try again");
                            working.setVisibility(View.VISIBLE);
                        } else {
                            mpg.setVisibility(View.VISIBLE);
                            bt.setEnabled(false);
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    cc1,
                                    60,
                                    TimeUnit.SECONDS,
                                    login_phone.this,
                                    mcall
                            );
                        }
                    }
                });
                mcall=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        working.setText("Verification failed please try again");
                        working.setVisibility(View.VISIBLE);
                        working.setVisibility(View.INVISIBLE);
                        bt.setEnabled(true);
                    }

                    @Override
                    public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        new android.os.Handler().postDelayed(
                                new Runnable(){
                                    public void run(){
                                        Intent intent=new Intent(login_phone.this,otp_phone.class);
                                        intent.putExtra("Auth-credentials",s);
                                        startActivity(intent);
                                    }
                                },
                                10000);

                    }
                };

            }
            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(login_phone.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sendUserToHome();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        working.setVisibility(View.VISIBLE);
                                        working.setText("There was an error on checking the otp");
                                    }
                                    mpg.setVisibility(View.INVISIBLE);
                                    bt.setEnabled(true);
                                }
                            }
                        });
            }
            private void sendUserToHome(){
                Intent intent=new Intent(login_phone.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }



            protected void onStart() {
                super.onStart();
                if (mUser != null){
                    sendUserToHome();
                }
            }
        }



