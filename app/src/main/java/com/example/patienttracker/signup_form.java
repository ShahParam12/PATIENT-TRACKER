package com.example.patienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup_form extends AppCompatActivity {

    EditText txtemail,txtpassword,txtconfirmpassword,name;
    Button btn_register;
    ProgressBar progess;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    Sign sign;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);





                name=(EditText)findViewById(R.id.name);
                txtemail=(EditText)findViewById(R.id.txt_email);
                txtpassword=(EditText)findViewById(R.id.txt_password);
                txtconfirmpassword=(EditText)findViewById(R.id.txt_confirmpass);
                progess=(ProgressBar)findViewById(R.id.progessb);
                btn_register=(Button)findViewById(R.id.btn_register);
                sign=new Sign();
                reff= FirebaseDatabase.getInstance().getReference().child("Sign");


                firebaseAuth = FirebaseAuth.getInstance();


                btn_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mail = name.getText().toString().trim();
                        String email = txtemail.getText().toString().trim();
                        String password = txtpassword.getText().toString().trim();
                        String confirm = txtconfirmpassword.getText().toString().trim();

                        sign.setName(name.getText().toString().trim());
                        sign.setEmail(txtemail.getText().toString().trim());
                        reff.push().setValue(sign);
                        Toast.makeText(signup_form.this,"DATA HAS BEEN SEND SUCCESSFULLY",Toast.LENGTH_LONG).show();




                        if(TextUtils.isEmpty(mail)){
                            Toast.makeText(signup_form.this,"ENTER YOUR FULL NAME",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if(TextUtils.isEmpty(email)){
                            Toast.makeText(signup_form.this,"ENTER AN EMAIL ADDRESS",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(password)){
                            Toast.makeText(signup_form.this,"ENTER A PASSWORD",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(confirm)){
                            Toast.makeText(signup_form.this,"PASSSWORD DOES NOT MATCH",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(password.length()<6) {
                            Toast.makeText(signup_form.this, "PASSWORD IS TOO SHORT MUST BE 6 CHARACTERS LENGTH", Toast.LENGTH_SHORT).show();
                        }

                        progess.setVisibility(View.VISIBLE);

                        if(password.equals(confirm)){

                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(signup_form.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            progess.setVisibility(View.GONE);


                                            if (task.isSuccessful()) {

                                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) ;
                                                        startActivity(new Intent(getApplicationContext(), login_form.class));
                                                        Toast.makeText(signup_form.this, "REGISTRATION SUCCESSFUL PLEASE VERIFY YOUR EMAIL ID", Toast.LENGTH_SHORT).show();
                                                    }

                                                });
                                            }

                                            else {
                                                Toast.makeText(signup_form.this,"EMAIL ID IS ALREADY USED",Toast.LENGTH_SHORT).show();
                                            }

                                            // ...
                                        }
                                    });

                        }
                        else{

                            Toast.makeText(signup_form.this,"PASSWORD DOES NOT MATCH",Toast.LENGTH_SHORT).show();
                        }



                    }
                });







            }
        }

