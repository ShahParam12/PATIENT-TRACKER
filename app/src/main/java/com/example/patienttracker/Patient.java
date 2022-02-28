package com.example.patienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Patient extends AppCompatActivity {
    EditText n;
    EditText ln;
    EditText agee;
    EditText dis;
    EditText add;
    Button jsk;
    DatabaseReference ref;
    poison poison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        n=(EditText)findViewById(R.id.editText);
        ln=(EditText)findViewById(R.id.editText2);
        agee=(EditText)findViewById(R.id.editText3);
        dis=(EditText)findViewById(R.id.editText4);
        add=(EditText)findViewById(R.id.editText5);
        jsk=(Button)findViewById(R.id.button);
        poison =new poison();
        ref= FirebaseDatabase.getInstance().getReference().child("poison");
        jsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String n1=n.getText().toString().trim();
                String ln1=ln.getText().toString().trim();
                String age1=agee.getText().toString().trim();
                final String dis1=dis.getText().toString().trim();
                String add1=add.getText().toString().trim();


                poison.setN1(n1.toString().trim());
                poison.setLn1(ln1);
                poison.setAge1(age1);
                poison.setDis1(dis1);
                poison.setAdd1(add1);

                if (!n1.isEmpty() && !ln1.isEmpty() && !age1.isEmpty() && !dis1.isEmpty() && !add1.isEmpty()) {
                    ref.push().setValue(poison);
                    Toast.makeText(Patient.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Patient.this,death.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Patient.this, "fill in the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

