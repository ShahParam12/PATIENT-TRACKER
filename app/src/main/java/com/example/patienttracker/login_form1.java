package com.example.patienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_form1 extends AppCompatActivity {

    EditText txtemail,txtpassword;
    Button btn_login, forgotpass;
    FirebaseAuth btn_sign;
    Button btn_signup;
    Button btn_phone;
    Button btn_google;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form1);



        txtemail=(EditText)findViewById(R.id.txt_email);
        txtpassword=(EditText)findViewById(R.id.txt_password);
        btn_login=(Button)findViewById(R.id.loginbtn);
        forgotpass=(Button)findViewById(R.id.btn_forgotpass);
        btn_signup=(Button)findViewById(R.id.signbtn);
        firebaseAuth = FirebaseAuth.getInstance();





        btn_phone=(Button)findViewById(R.id.phone);
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(login_form1.this,login_phone1.class);
                startActivity(intent);
            }
        });

        btn_google=(Button)findViewById(R.id.button);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(login_form1.this,login_google1.class);
                startActivity(intent);
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(login_form1.this,"ENTER AN EMAIL ADDRESS",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(login_form1.this,"ENTER A PASSWORD",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6) {
                    Toast.makeText(login_form1.this, "PASSWORD IS TOO SHORT MUST BE 6 CHARACTERS LENGTH", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login_form1.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if (firebaseAuth.getCurrentUser().isEmailVerified()){

                                        Toast.makeText(login_form1.this,"LOGGED IN SUCCESSFULLY",Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(),Main3Activity.class));

                                    }


                                } else {

                                    Toast.makeText(login_form1.this,"LOG IN FAILED PLEASE VERIFY YOUR EMAIL ID",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login_form1.this,Forgotpassword.class);
                startActivity(i);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(login_form1.this,signup_form1.class);
                startActivity(intent);
            }
        });
    }


    public void btn_signup(View view){

        startActivity(new Intent(getApplicationContext(),signup_form1.class));
    }
}

