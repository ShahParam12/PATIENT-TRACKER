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

public class doctor extends AppCompatActivity {
    EditText namee;
    EditText lastnamee;
    EditText special;
    EditText agee;
    EditText number;
    Button bt;
    DatabaseReference doct;
    Doct Doct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        namee = (EditText) findViewById(R.id.editText);
        lastnamee = (EditText) findViewById(R.id.editText2);
        special = (EditText) findViewById(R.id.editText3);
        agee = (EditText) findViewById(R.id.editText4);
        number = (EditText) findViewById(R.id.editText5);
        bt = (Button) findViewById(R.id.button);
        Doct = new Doct();
        doct = FirebaseDatabase.getInstance().getReference().child("Doct");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = namee.getText().toString().trim();
                String lastname = lastnamee.getText().toString().trim();
                String speacilist = special.getText().toString().trim();
                String age = agee.getText().toString().trim();
                String phone = number.getText().toString().trim();

                Doct.setName(name.toString().trim());
                Doct.setLastname(lastname);
                Doct.setSpecialist(speacilist);
                Doct.setAge(age);
                Doct.setPhone(phone);


                if (!name.isEmpty() && !lastname.isEmpty() && !age.isEmpty() && !speacilist.isEmpty() && !phone.isEmpty()) {
                    doct.push().setValue(Doct);
                    Toast.makeText(doctor.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(doctor.this, Doctor1.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(doctor.this, "fill in the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



